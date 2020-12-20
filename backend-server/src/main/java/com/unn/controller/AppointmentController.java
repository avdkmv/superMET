package com.unn.controller;

import com.unn.model.Appointment;
import com.unn.service.impl.AppointmentService;
import com.unn.service.impl.ValidationService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ValidationService validationService;

    @GetMapping("/create/{id}")
    public ResponseEntity<Appointment> setAppointmentAsBusy(@PathVariable(name = "id") Long id) {
        Optional<Appointment> appointment = appointmentService.findAppointment(id);
        if (appointment.isPresent()) {
            appointment.get().setBusy(true);
            return ResponseEntity.ok(appointmentService.updateAppointment(appointment.get()).get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

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
    public ResponseEntity<Appointment> getAppointment(
        @PathVariable(name = "doctorId") Long doctorId,
        @PathVariable(name = "patientId") Long patientId
    ) {
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
    public ResponseEntity<Appointment> deleteAppointment(
        @PathVariable(name = "doctorId") Long doctorId,
        @PathVariable(name = "patientId") Long patientId
    ) {
        Optional<Appointment> appointment = appointmentService.deleteAppointment(doctorId, patientId);
        if (appointment.isPresent()) {
            return ResponseEntity.ok(appointment.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/create/doctor/{doctorId}/date/{date}")
    public ResponseEntity<Appointment> createAppointment(
        @PathVariable(name = "doctorId") Long doctorId,
        @PathVariable(name = "date") Date date
    ) {
        if (validationService.validateAppointmentCreation(doctorId)) {
            Optional<Appointment> appointment = appointmentService.createAppointment(doctorId, date);
            return ResponseEntity.ok(appointment.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/doctor/free/{id}")
    public ResponseEntity<List<Appointment>> getDoctorFreeAppointment(@PathVariable(name = "id") Long id) {
        Optional<List<Appointment>> appointments = appointmentService.findAllFreeDoctorAppointment(id);
        if (appointments.isPresent()) {
            return ResponseEntity.ok(appointments.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/doctor/busy/{id}")
    public ResponseEntity<List<Appointment>> getDoctorBusyAppointment(@PathVariable(name = "id") Long id) {
        Optional<List<Appointment>> appointments = appointmentService.findAllBusyDoctorAppointment(id);
        if (appointments.isPresent()) {
            return ResponseEntity.ok(appointments.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/patient/free/{id}")
    public ResponseEntity<List<Appointment>> getPatientFreeAppointment(@PathVariable(name = "id") Long id) {
        Optional<List<Appointment>> appointments = appointmentService.findAllFreePatientAppointment(id);
        if (appointments.isPresent()) {
            return ResponseEntity.ok(appointments.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/patient/busy/{id}")
    public ResponseEntity<List<Appointment>> getPatientBusyAppointment(@PathVariable(name = "id") Long id) {
        Optional<List<Appointment>> appointments = appointmentService.findAllBusyPatientAppointment(id);
        if (appointments.isPresent()) {
            return ResponseEntity.ok(appointments.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/day/free/{day}")
    public ResponseEntity<List<Appointment>> getFreeAppointmentByDay(@PathVariable(name = "day") Long day) {
        Optional<List<Appointment>> appointments = appointmentService.findAllFreeAppointmentsByDay(day);
        if (appointments.isPresent()) {
            return ResponseEntity.ok(appointments.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/day/free/ratio/{day}")
    public ResponseEntity<String> getRatioByDay(@PathVariable(name = "day") Long day) {
        Optional<String> ratio = appointmentService.getRatioFreeAllByDay(day);
        if (ratio.isPresent()) {
            return ResponseEntity.ok(ratio.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
