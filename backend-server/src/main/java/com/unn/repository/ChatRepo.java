package com.unn.repository;

import com.unn.model.Appointment;
import com.unn.model.Chat;

import com.unn.model.Doctor;
import com.unn.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {
    Optional<Chat> findByDoctorIdAndPatientId(Doctor doctorId, Patient patientId);
}
