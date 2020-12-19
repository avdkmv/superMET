package com.unn.service.impl;

import java.util.Date;

import com.unn.model.Appointment;
import com.unn.model.Calendar;
import com.unn.model.Doctor;
import com.unn.model.Calendar;
import com.unn.repository.AppointmentRepo;
import com.unn.repository.CalendarRepo;
import com.unn.repository.DoctorRepo;
import com.unn.service.ICalendarService;
import com.unn.service.IValidationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.Return;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.util.OptionHelper;

@Service
@RequiredArgsConstructor
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

      Map<Long, Appointment> apm;
      Optional<List<Appointment>> appointments = appointmentRepo.findAllByDoctorId(doctorId);

      if (appointments.isPresent()) {
        apm = appointments.get().stream().collect(
              Collectors.toMap(Appointment::getId, appointment -> appointment)
            );

        calendar.setAppointments(apm);
        
        Optional<Doctor> doctor = doctorRepo.findById(doctorId);
        calendar.setDoctor(doctor.get());
      }
    }

    return Optional.of(calendar);
  }

  @Override
  public Optional<Calendar> findCalendar(Long calendarId) {
    return calendarRepo.findById(calendarId);
  }

  @Override
  public Optional<Calendar> modifyCalendar(Long calendarId) {
    Optional<Calendar> calendar = calendarRepo.findById(calendarId);

    if (calendar.isPresent()) {
      Doctor doctor = calendar.get().getDoctor();
      Long doctorId = doctor.getId();

      Map<Long, Appointment> apm;
      Optional<List<Appointment>> appointments = appointmentRepo.findAllByDoctorId(doctorId);

      if (appointments.isPresent()) {
        apm = appointments.get().stream().collect(
              Collectors.toMap(Appointment::getId, appointment -> appointment)
            );

        calendar.get().setAppointments(apm);
        calendarRepo.save(calendar.get());
      }
    }

    return calendar;
  }

  @Override
  public Optional<Calendar> deleteCalendar(Long calendarId) {
    Optional<Calendar> calendar = calendarRepo.findById(calendarId);
    
    if (calendar.isPresent()) {
      calendarRepo.delete(calendar.get());
    }
    
    return calendar;
  }
}
