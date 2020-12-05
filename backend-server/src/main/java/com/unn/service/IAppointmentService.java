package com.unn.service;

import java.util.Optional;

import com.unn.model.Appointment;

public interface IAppointmentService {
  Optional<Appointment> createAppointment(Long doctorId, Long patientId);

  Optional<Appointment> findAppointment(Long appointmentId);
  Optional<Appointment> findAppointment(Long doctorId, Long patientId);

  Optional<Appointment> deleteAppointment(Long appointmentId);
  Optional<Appointment> deleteAppointment(Long doctorId, Long patientId);

  Optional<Appointment> newResult(Long appointmentId, Long documentId);
  Optional<Appointment> newResult(
    Long appointmentId,
    Long doctorId,
    Long patientId
  );
}
