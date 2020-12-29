package com.unn.service.impl;

import java.time.LocalDateTime;
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

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime endDate = currentDate.plusDays(14); // schedule for two weeks

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
    @Scheduled(cron = "0 0 0 * * 0")
    public void modifyCalendar() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Calendar> allCalendars = calendarRepo.findAll();
        allCalendars.forEach(
            calendar -> {
                // deleting last week
                calendar
                    .getAppointments()
                    .forEach(
                        (appointmentId, appointment) -> {
                            if (appointment.getDate().isBefore(currentDate)) {
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
        }

        return calendar;
    }

    private void createAppointments(
        LocalDateTime currentDate,
        LocalDateTime endDate,
        int startTime,
        int endTime,
        Calendar calendar
    ) {
        for (LocalDateTime startDate = currentDate; startDate.isBefore(endDate); startDate = startDate.plusDays(1)) {
            if (startDate.getDayOfWeek().getValue() != 6 && startDate.getDayOfWeek().getValue() != 7) {
                LocalDateTime endDateTime = startDate.withHour(endTime).withMinute(0).withSecond(0);
                for (
                    LocalDateTime dateTime = startDate.withHour(startTime).withMinute(0).withSecond(0);
                    dateTime.isBefore(endDateTime);
                    dateTime = dateTime.plusHours(1).plusMinutes(5)
                ) {
                    Appointment newAppointment = new Appointment(calendar.getDoctor(), dateTime, calendar);
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
