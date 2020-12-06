package com.unn.repository;

import com.unn.model.Appointment;

import com.unn.model.Doctor;
import com.unn.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByDoctorIdAndPatientId(Doctor doctorId, Patient patientId);

    @Modifying
    @Transactional
    @Query(value="delete from Appointment a where a.doctorId.id = ?1 and a.patientId.id = ?2")
    void deleteByDoctorIdAndPatientId(Long doctorId, Long patientId);
}
