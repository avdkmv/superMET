package com.unn.service;

import java.util.Optional;

import com.unn.model.Calendar;

public interface ICalendarService {
    Optional<Calendar> createCalendarByDoctorID(Long doctorId, int startTime, int endTime);

    Optional<Calendar> findCalendar(Long calendarId);

    void modifyCalendar();

    Optional<Calendar> deleteCalendar(Long calendarId);
}
