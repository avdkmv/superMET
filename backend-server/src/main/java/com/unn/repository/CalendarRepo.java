package com.unn.repository;

import com.unn.model.Calendar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepo extends JpaRepository<Calendar, Long> {
    public Optional<Calendar> findByDoctorId(Long doctorId);
}
