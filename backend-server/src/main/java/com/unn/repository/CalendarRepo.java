package com.unn.repository;

import java.util.Optional;

import com.unn.model.Calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepo extends JpaRepository<Calendar, Long> {
    Optional<Calendar> getByID(Long calendarId);
    Optional<Calendar> getByDoctorID(Long doctorId);
}
