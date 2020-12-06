package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.Appointment;
import com.unn.model.Doctor;
import com.unn.model.Patient;
import com.unn.repository.AppointmentRepo;
import com.unn.repository.DoctorRepo;
import com.unn.repository.DocumentRepo;
import com.unn.repository.PatientRepo;
import com.unn.service.IAppointmentService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
  private final AppointmentRepo appointmentRepo;
  private final DoctorRepo doctorRepo;
  private final PatientRepo patientRepo;

  @Override
  public Optional<Appointment> createAppointment(
    Long doctorId,
    Long patientId
  ) {
    Optional<Doctor> doctor = doctorRepo.findById(doctorId);
    Optional<Patient> patient = patientRepo.findById(patientId);
    Appointment appointment = new Appointment(doctor.get(), patient.get());
    appointmentRepo.save(appointment);
    return Optional.of(appointment);
  }

  @Override
  public Optional<Appointment> findAppointment(Long appointmentId) {
    return appointmentRepo.findById(appointmentId);
  }

  @Override
  public Optional<Appointment> findAppointment(Long doctorId, Long patientId) {
    Optional<Patient> patient = patientRepo.findById(patientId);
    Optional<Doctor> doctor = doctorRepo.findById(doctorId);
    return appointmentRepo.findByDoctorIdAndPatientId(doctor.get(), patient.get());
  }
  
  @Override
  public void deleteAppointment(Long appointmentId) {
    appointmentRepo.deleteById(appointmentId);
  }

  @Override
  public void deleteAppointment(
    Long doctorId,
    Long patientId
  ) {
  // WA: this implementation doesnt remove entity
  // Optional<Appointment> appointment = findAppointment(doctorId, patientId);
  // appointmentRepo.deleteById(appointment.get().getId());
    appointmentRepo.deleteByDoctorIdAndPatientId(doctorId, patientId);
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
