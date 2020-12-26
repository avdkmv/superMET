package com.unn.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.unn.model.Appointment;
import com.unn.model.Calendar;
import com.unn.model.Doctor;
import com.unn.repository.AppointmentRepo;
import com.unn.repository.CalendarRepo;
import com.unn.service.ICalendarService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class CalendarService implements ICalendarService {
    private final CalendarRepo calendarRepo;
    // private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;

    private final ValidationService validationService;

    @Override
    public Optional<Calendar> createCalendarByDoctorId(Doctor doctor, int startTime, int endTime) {
        if (!validationService.validateWorkTime(startTime, endTime)) {
            return Optional.empty();
        }

        Calendar calendar = new Calendar();
        calendar.setStartTime(startTime);
        calendar.setEndTime(endTime);
        calendar.setDoctor(doctor);

        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.plusDays(14); // schedule for two weeks

        Calendar savedCalendar = calendarRepo.save(calendar);
        doctor.setCalendar(savedCalendar);
        createAppointments(currentDate, endDate, startTime, endTime, savedCalendar);

        return Optional.of(savedCalendar);
    }

    @Override
    public Optional<Calendar> findCalendar(Long calendarId) {
        return calendarRepo.findById(calendarId);
    }

    @Override
    public Optional<Calendar> findCalendarByDoctorId(Long doctorId) {
        return calendarRepo.findByDoctorId(doctorId);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * 0")
    public void modifyCalendar() {
        LocalDate currentDate = LocalDate.now();
        List<Calendar> allCalendars = calendarRepo.findAll();
        allCalendars.forEach(
            calendar -> {
                // deleting last week
                Date newStartDate = java.sql.Date.valueOf(currentDate);
                calendar
                    .getAppointments()
                    .forEach(
                        (appointmentId, appointment) -> {
                            if (appointment.getDate().before(newStartDate)) {
                                appointmentRepo.deleteById(appointmentId);
                                calendar.getAppointments().remove(appointmentId);
                            }
                        }
                    );
                // filling next week
                createAppointments(
                    currentDate.plusDays(7),
                    currentDate.plusDays(14),
                    calendar.getStartTime(),
                    calendar.getEndTime(),
                    calendar
                );
                calendarRepo.save(calendar);
            }
        );
    }

    @Override
    public Optional<Calendar> deleteCalendar(Long calendarId) {
        Optional<Calendar> calendar = calendarRepo.findById(calendarId);

        if (calendar.isPresent()) {
            calendarRepo.delete(calendar.get());
            calendar
                .get()
                .getAppointments()
                .forEach((appointmentId, appointment) -> appointmentRepo.deleteById(appointmentId));
        }

        return calendar;
    }

    private void createAppointments(
        LocalDate currentDate,
        LocalDate endDate,
        int startTime,
        int endTime,
        Calendar calendar
    ) {
        java.util.Calendar appointmentCalendar = java.util.Calendar.getInstance();

        for (LocalDate startDate = currentDate; startDate.isBefore(endDate); startDate = startDate.plusDays(1)) {
            if (startDate.getDayOfWeek().getValue() != 6 && startDate.getDayOfWeek().getValue() != 7) {
                Date appointmentDate = java.sql.Date.valueOf(startDate);
                appointmentCalendar.setTime(appointmentDate);

                for (
                    appointmentCalendar.set(java.util.Calendar.HOUR_OF_DAY, startTime);
                    appointmentCalendar.get(java.util.Calendar.HOUR_OF_DAY) <= endTime;
                    appointmentCalendar.add(java.util.Calendar.HOUR_OF_DAY, 1), appointmentCalendar.add(
                        java.util.Calendar.MINUTE,
                        5
                    )
                ) {
                    Appointment newAppointment = new Appointment(
                        calendar.getDoctor(),
                        appointmentCalendar.getTime(),
                        calendar
                    );
                    appointmentRepo.save(newAppointment);
                }
            }
        }
    }

    @Override
    public void clearTable() {
        calendarRepo.deleteAll();
    }
}
