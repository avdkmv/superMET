package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.Appointment;
import com.unn.repository.AppointmentRepo;
import com.unn.service.IAppointmentService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
  private final AppointmentRepo appointmentRepo;

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
