package com.unn.service;

import java.util.List;
import java.util.Optional;

import com.unn.model.Appointment;

public interface IAppointmentService {
  Optional<Appointment> createAppointment(Appointment appointment);

  Optional<Appointment> findAppointment(Long appointmentId);
  Optional<Appointment> findAppointment(Long doctorId, Long patientId);

  Optional<List<Appointment>> findAllBusyDoctorAppointment(Long doctorId);
  Optional<List<Appointment>> findAllFreeDoctorAppointment(Long doctorId);

  Optional<List<Appointment>> findAllBusyPatientAppointment(Long patientId);
  Optional<List<Appointment>> findAllFreePatientAppointment(Long patientId);

  Optional<Appointment> deleteAppointment(Long appointmentId);
  Optional<Appointment> deleteAppointment(Long doctorId, Long patientId);

  Optional<Appointment> newResult(Long appointmentId, Long documentId);
  Optional<Appointment> newResult(
    Long appointmentId,
    Long doctorId,
    Long patientId
  );
}
