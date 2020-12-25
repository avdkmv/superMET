package com.unn.service;

import java.util.Optional;
import com.unn.model.Calendar;
import com.unn.model.Doctor;

public interface ICalendarService {
    Optional<Calendar> createCalendarByDoctorId(Doctor dcotor, int startTime, int endTime);

    Optional<Calendar> findCalendar(Long calendarId);

    void modifyCalendar();

    Optional<Calendar> deleteCalendar(Long calendarId);
}
