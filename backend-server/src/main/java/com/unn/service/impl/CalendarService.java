package com.unn.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.unn.model.Appointment;
import com.unn.model.Calendar;
import com.unn.model.Doctor;
import com.unn.repository.AppointmentRepo;
import com.unn.repository.CalendarRepo;
import com.unn.repository.DoctorRepo;
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
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;

    private final ValidationService validationService;
    private final AppointmentService appointmentService;

    @Override
    public Optional<Calendar> createCalendarByDoctorID(Long doctorId, int startTime, int endTime) {
        Calendar calendar = new Calendar();

        if (validationService.validateWorkTime(startTime, endTime)) {
            calendar.setStartTime(startTime);
            calendar.setEndTime(endTime);

            Optional<Doctor> doctor = doctorRepo.findById(doctorId);
            calendar.setDoctor(doctor.get());

            Map<Long, Appointment> appointments;

            LocalDate currentDate = LocalDate.now();
            LocalDate endDate = currentDate.plusDays(14); // schedule for two weeks
            for (LocalDate startDate = currentDate; startDate.isBefore(endDate); startDate = startDate.plusDays(1)) {
                if (startDate.getDayOfWeek().getValue() != 6 && startDate.getDayOfWeek().getValue() != 7) {
                    for (int startHour = startTime; startHour < endTime; startHour++) {
                        Date appointmentDate = java.sql.Date.valueOf(startDate);
                        Appointment newAppointment = new Appointment(doctorId, appointmentDate.setHours(startHour));
                        appointmentRepo.save(newAppointment);
                        appointments.put(newAppointment.getId(), newAppointment);
                    }
                }
            }

            calendar.setAppointments(appointments);
        }
        calendarRepo.save(calendar);
        return Optional.of(calendar);
    }

    @Override
    public Optional<Calendar> findCalendar(Long calendarId) {
        return calendarRepo.findById(calendarId);
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
                for (
                    LocalDate nextWeekStartDate = currentDate.plusDays(7);
                    nextWeekStartDate.isBefore(currentDate.plusDays(14));
                    nextWeekStartDate = nextWeekStartDate.plusDays(1)
                ) {
                    if (
                        nextWeekStartDate.getDayOfWeek().getValue() != 6 &&
                        nextWeekStartDate.getDayOfWeek().getValue() != 7
                    ) {
                        for (int startHour = calendar.getStartTime(); startHour < calendar.getEndTime(); startHour++) {
                            Date appointmentDate = java.sql.Date.valueOf(nextWeekStartDate);
                            Appointment newAppointment = new Appointment(
                                calendar.getDoctor().getId(),
                                appointmentDate.setHours(startHour)
                            );
                            appointmentRepo.save(newAppointment);
                            calendar.getAppointments().put(newAppointment.getId(), newAppointment);
                        }
                    }
                }
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
}
