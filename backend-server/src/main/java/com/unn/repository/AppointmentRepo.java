package com.unn.repository;

import com.unn.model.Appointment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByDoctorIdAndPatientId(Long doctorId, Long patientId);

    @Transactional
    void deleteByDoctorIdAndPatientId(Long doctorId, Long patientId);

    Optional<List<Appointment>> findAllByDoctorIdAndBusy(Long doctorId, boolean busy);
    Optional<List<Appointment>> findAllByPatientIdAndBusy(Long doctorId, boolean busy);
    Optional<List<Appointment>> findAllByDoctorId(Long doctorId);
}
