package com.unn.repository;

import com.unn.model.Facility;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepo extends JpaRepository<Facility, Long> {
    Optional<Facility> findByName(String name);
    Optional<Facility> findByDoctorsId(Long doctorId);
}
