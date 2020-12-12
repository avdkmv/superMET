package com.unn.service;

import java.util.Optional;

import com.unn.model.Calendar;
import com.unn.model.Schedule;

public interface IScheduleService {
  Optional<Schedule> createSchedule(Long calendarId);

  Optional<Schedule> createScheduleByDoctorID(Long doctorId, int startTime, int endTime);

  Optional<Schedule> deleteSchedule(Long calendarId);

  Optional<Schedule> findCalendar(Long calendarId);
  
  Optional<Schedule> modifyCalendar(Long calendarId);

  Optional<Schedule> deleteCalendar(Long calendarId);
}
