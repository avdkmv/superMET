package com.unn.service.impl;

import com.unn.constants.Constant;
import com.unn.constants.UserTypes;
import com.unn.model.Appointment;
import com.unn.model.Calendar;
import com.unn.model.Facility;
import com.unn.model.User;
import com.unn.repository.AppointmentRepo;
import com.unn.repository.CalendarRepo;
import com.unn.repository.FacilityRepo;
import com.unn.repository.UserRepo;
import com.unn.service.IMetricsService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricsService implements IMetricsService {
    private final UserRepo userRepo;
    private final FacilityRepo facilityRepo;
    private final CalendarRepo calendarRepo;
    private final AppointmentRepo appointmentsRepo;
    private final Logger logger = LogManager.getLogger(MetricsService.class);

    @Override
    public Optional<File> createStatistic() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Statistic");
        Date currentDate = new Date();

        // fill title
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("Doctor name");
        titleRow.createCell(1).setCellValue("Facility");
        titleRow.createCell(2).setCellValue("Work time");
        titleRow.createCell(3).setCellValue("Appointments count");
        titleRow.createCell(4).setCellValue("Efficiency in working days");

        List<User> doctors = userRepo.findAllByTypeId(UserTypes.DOCTOR.getId()).get();
        if (doctors.isEmpty()) {
            return Optional.empty();
        }

        for (int row = 1; row < doctors.size() + 1; row++) {
            Row currentRow = sheet.createRow(row);
            User currentDoctor = doctors.get(row - 1);
            Long currentDoctorId = currentDoctor.getId();

            //fill name
            currentRow.createCell(0).setCellValue(currentDoctor.getUsername());

            //fill facility
            Optional<Facility> facility = facilityRepo.findByDoctorsId(currentDoctorId);
            if (facility.isPresent()) {
                currentRow.createCell(1).setCellValue(Constant.METRIC_NOT_SPECIFIED);
            } else {
                currentRow.createCell(1).setCellValue(facility.get().getName());
            }

            //fill work time
            Optional<Calendar> doctorCalendar = calendarRepo.findByDoctorId(currentDoctorId);
            if (doctorCalendar.isPresent()) {
                currentRow.createCell(2).setCellValue(Constant.METRIC_NOT_SPECIFIED);
            } else {
                currentRow
                    .createCell(2)
                    .setCellValue(doctorCalendar.get().getEndTime() - doctorCalendar.get().getStartTime());
            }

            // params for efficiency
            Optional<List<Appointment>> allDoctorAppointments = appointmentsRepo.findAllByDoctorId(currentDoctorId);
            if (allDoctorAppointments.isPresent()) {
                float busyAppointments = getBusykAppointmentsCountForNow(currentDate, allDoctorAppointments.get());
                float allAppointments = getAllAppointmentsCountForNow(currentDate, allDoctorAppointments.get());
                String efficiency = new DecimalFormat("#0.00")
                .format(Float.isNaN(busyAppointments / allAppointments) ? 0.00 : busyAppointments / allAppointments);

                //fill appointments count
                currentRow.createCell(3).setCellValue(busyAppointments);

                //fill efficiency in working days
                currentRow.createCell(4).setCellValue(efficiency);
            } else {
                //fill appointments count
                currentRow.createCell(3).setCellValue(0);

                //fill efficiency in working days
                currentRow.createCell(4).setCellValue(0.00);
            }
            
        }

        for (int col = 0; col < 5; col++) sheet.autoSizeColumn(col);

        File file = new File("./statistic.xls");
        try {
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return Optional.of(file);
    }

    private float getBusykAppointmentsCountForNow(Date date, List<Appointment> allDoctorAppointments) {
        return (float) allDoctorAppointments
            .stream()
            .filter(appointment -> appointment.getDate().before(date) && appointment.isBusy())
            .count();
    }

    private float getAllAppointmentsCountForNow(Date date, List<Appointment> allDoctorAppointments) {
        return (float) allDoctorAppointments.stream().filter(appointment -> appointment.getDate().before(date)).count();
    }
}
