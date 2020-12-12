package com.unn.controller;

import com.unn.model.Appointment;
import com.unn.service.impl.AppointmentService;

import com.unn.service.impl.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
  private final AppointmentService appointmentService;
  private final ValidationService validationService;

  @GetMapping("/{id}")
  public ResponseEntity<Appointment> getAppointment(@PathVariable(name = "id") Long id) {
    Optional<Appointment> appointment = appointmentService.findAppointment(id);
    if (appointment.isPresent()) {
      return ResponseEntity.ok(appointment.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @GetMapping("/doctor/{doctorId}/patient/{patientId}")
  public ResponseEntity<Appointment> getAppointment(@PathVariable(name = "doctorId") Long doctorId,
                                                    @PathVariable(name = "patientId") Long patientId) {
    Optional<Appointment> appointment = appointmentService.findAppointment(doctorId, patientId);
    if (appointment.isPresent()) {
      return ResponseEntity.ok(appointment.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Appointment> deleteAppointment(@PathVariable(name = "id") Long id) {
    Optional<Appointment> appointment = appointmentService.deleteAppointment(id);
    if (appointment.isPresent()) {
      return ResponseEntity.ok(appointment.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/doctor/{doctorId}/patient/{patientId}/delete")
  public ResponseEntity<Appointment> deleteAppointment(@PathVariable(name = "doctorId") Long doctorId,
                                                       @PathVariable(name = "patientId") Long patientId) {
    Optional<Appointment> appointment = appointmentService.deleteAppointment(doctorId, patientId);
    if (appointment.isPresent()) {
      return ResponseEntity.ok(appointment.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/create")
  public ResponseEntity<Appointment> createAppointment(@RequestBody @Valid Appointment appointment) {
    if (validationService.validateAppointmentCreation(appointment)) {
      appointmentService.createAppointment(appointment);
      return ResponseEntity.ok(appointment);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
