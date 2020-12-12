package com.unn.service.impl;

import com.unn.model.Appointment;
import com.unn.model.Calendar;
import com.unn.model.Doctor;
import com.unn.model.Schedule;
import com.unn.repository.AppointmentRepo;
import com.unn.repository.CalendarRepo;
import com.unn.repository.DoctorRepo;
import com.unn.service.IScheduleService;
import com.unn.service.IValidationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
  private final CalendarRepo calendarRepo;
  private final DoctorRepo doctorRepo;
  private final AppointmentRepo appointmentRepo;

  private final ValidationService validationService;

  @Override
  public Optional<Schedule> createSchedule(Long calendarId) {
    return null;
  }

  @Override
  public Optional<Schedule> createScheduleByDoctorID(
    Long doctorId,
    int startTime,
    int endTime
  ) {
    if (validationService.validateWorkTime(startTime, endTime)) {
      Optional<Doctor> doctor = doctorRepo.findById(doctorId);

      // create appointments
      Map<Long, Appointment> apm;

      Optional<List<Appointment>> appointments = appointmentRepo.findAllByDoctorId(
        doctorId
      );

      if (appointments.isPresent()) {
        apm =
          appointments
            .get()
            .stream()
            .collect(
              Collectors.toMap(Appointment::getId, appointment -> appointment)
            );
      }

      
    }

    return null;
  }

  @Override
  public Optional<Schedule> deleteSchedule(Long calendarId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Schedule> findCalendar(Long calendarId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Schedule> modifyCalendar(Long calendarId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Schedule> deleteCalendar(Long calendarId) {
    // TODO:  implement method
    return null;
  }
}
