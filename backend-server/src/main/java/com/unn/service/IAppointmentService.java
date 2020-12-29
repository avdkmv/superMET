package com.unn.service;

import java.util.List;
import java.util.Optional;
import com.unn.model.Appointment;
import org.springframework.security.core.Authentication;

public interface IAppointmentService {
    // Optional<Appointment> createAppointment(Long doctorId, Date date);

    Optional<Appointment> findAppointment(Long appointmentId);
    Optional<Appointment> findAppointment(Long doctorId, Long patientId);

    Optional<List<Appointment>> findBusyAppointmentsByDoctor(Long doctorId);
    Optional<List<Appointment>> findFreeAppointmentsByDoctor(Long doctorId);

    Optional<List<Appointment>> findBusyAppointmentsByPatient(Authentication auth);
    Optional<List<Appointment>> findFreeAppointmentsByPatient(Authentication auth);

    Optional<List<Appointment>> findFreeAppointmentsByDay(Long day, Long doctorId);
    Optional<String> countFreeAppointmetnsPerDay(Long day, Long doctorId);

    Optional<Appointment> updateAppointment(Appointment appointment);

    Optional<Appointment> deleteAppointment(Long appointmentId);
    Optional<Appointment> deleteAppointment(Long doctorId, Long patientId);

    Optional<Appointment> newResult(Long appointmentId, Long documentId);
    Optional<Appointment> newResult(Long appointmentId, Long doctorId, Long patientId);

    void clearTable();

    Optional<List<Appointment>> findAll();
}
