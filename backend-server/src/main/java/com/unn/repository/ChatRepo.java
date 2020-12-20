package com.unn.repository;

import com.unn.model.Chat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {
    Optional<Chat> findByDoctorIdAndPatientId(Long doctorId, Long patientId);
}
