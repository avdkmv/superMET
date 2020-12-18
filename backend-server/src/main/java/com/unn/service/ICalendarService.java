package com.unn.service;

import java.util.Optional;

import com.unn.model.Calendar;
import com.unn.model.Calendar;

public interface ICalendarService {
  Optional<Calendar> createCalendar(Long calendarId);

  Optional<Calendar> createCalendarByDoctorID(Long doctorId, int startTime, int endTime);

  Optional<Calendar> findCalendar(Long calendarId);
  
  Optional<Calendar> modifyCalendar(Long calendarId);

  Optional<Calendar> deleteCalendar(Long calendarId);
}
