package com.unn.service;

import com.unn.model.Calendar;
import java.util.Optional;

public interface ICalendarService {
    Optional<Calendar> createCalendarByDoctorID(Long doctorId, int startTime, int endTime);

    Optional<Calendar> findCalendar(Long calendarId);

    void modifyCalendar();

    Optional<Calendar> deleteCalendar(Long calendarId);
}
