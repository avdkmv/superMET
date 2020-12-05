package com.unn.service;

import java.util.Optional;

import com.unn.model.Calendar;

public interface IScheduleService {
  Optional<Calendar> createSchedule(Long calendarId);

  Optional<Calendar> deleteSchedule(Long calendarId);

  Optional<Calendar> findCalendar(Long calendarId);
  
  Optional<Calendar> modifyCalendar(Long calendarId);

  Optional<Calendar> deleteCalendar(Long calendarId);
}
