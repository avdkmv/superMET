package com.unn.service.impl;

import com.unn.model.Appointment;
import com.unn.service.IAppointmentService;
import java.util.Optional;

public class AppointmentService implements IAppointmentService {

  @Override
  public Optional<Appointment> createAppointment(
    Long doctorId,
    Long patientId
  ) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Appointment> findAppointment(Long appointmentId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Appointment> findAppointment(Long doctorId, Long patientId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Appointment> deleteAppointment(Long appointmentId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Appointment> deleteAppointment(
    Long doctorId,
    Long patientId
  ) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Appointment> newResult(Long appointmentId, Long documentId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Appointment> newResult(
    Long appointmentId,
    Long doctorId,
    Long patientId
  ) {
    // TODO:  implement method
    return null;
  }
}
