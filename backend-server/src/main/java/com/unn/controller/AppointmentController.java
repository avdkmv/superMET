package com.unn.controller;

import java.util.List;
import com.unn.model.Appointment;
import com.unn.service.impl.AppointmentService;
import com.unn.service.impl.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    private final ResponseService responseService;

    @PostMapping("/create/{id}")
    public ResponseEntity<Appointment> makeAppointment(@PathVariable(name = "id") Long id) {
        return responseService.handleGetResponse(appointmentService.makeAppointment(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable(name = "id") Long id) {
        return responseService.handleGetResponse(appointmentService.findAppointment(id));
    }

    @GetMapping("/doctor/{doctorId}/patient/{patientId}")
    public ResponseEntity<Appointment> getAppointment(
        @PathVariable(name = "doctorId") Long doctorId,
        @PathVariable(name = "patientId") Long patientId
    ) {
        return responseService.handleGetResponse(appointmentService.findAppointment(doctorId, patientId));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable(name = "id") Long id) {
        return responseService.handleDeleteResponse(appointmentService.deleteAppointment(id));
    }

    @DeleteMapping("/doctor/{doctorId}/patient/{patientId}/delete")
    public ResponseEntity<Appointment> deleteAppointment(
        @PathVariable(name = "doctorId") Long doctorId,
        @PathVariable(name = "patientId") Long patientId
    ) {
        return responseService.handleDeleteResponse(appointmentService.deleteAppointment(doctorId, patientId));
    }

    // @PostMapping("/create/doctor/{doctorId}/date/{date}")
    // public ResponseEntity<Appointment> createAppointment(
    //     @PathVariable(name = "doctorId") Long doctorId,
    //     @PathVariable(name = "date") Date date
    // ) {
    //     return responseService.handlePostResponse(appointmentService.createAppointment(doctorId, date));
    // }

    @GetMapping("/doctor/{id}/free")
    public ResponseEntity<List<Appointment>> getDoctorFreeAppointments(@PathVariable(name = "id") Long id) {
        return responseService.handleGetResponse(appointmentService.findFreeAppointmentsByDoctor(id));
    }

    @GetMapping("/doctor/{id}/busy")
    public ResponseEntity<List<Appointment>> getDoctorBusyAppointment(@PathVariable(name = "id") Long id) {
        return responseService.handleGetResponse(appointmentService.findBusyAppointmentsByDoctor(id));
    }

    @GetMapping("/doctor/{id}/free/{day}")
    public ResponseEntity<List<Appointment>> getFreeAppointmentByDay(
        @PathVariable(name = "day") Long day,
        @PathVariable(name = "id") Long doctorId
    ) {
        return responseService.handleGetResponse(appointmentService.findFreeAppointmentsByDay(day, doctorId));
    }

    @GetMapping("/doctor/{id}/free/{day}/ratio")
    public ResponseEntity<String> getRatioByDay(
        @PathVariable(name = "day") Long day,
        @PathVariable(name = "id") Long doctorId
    ) {
        return responseService.handleGetResponse(appointmentService.countFreeAppointmetnsPerDay(day, doctorId));
    }

    @GetMapping("/patient/{id}/free")
    public ResponseEntity<List<Appointment>> getPatientFreeAppointment(@PathVariable(name = "id") Long id) {
        return responseService.handleGetResponse(appointmentService.findFreeAppointmentsByPatient(id));
    }

    @GetMapping("/patient/{id}/busy")
    public ResponseEntity<List<Appointment>> getPatientBusyAppointment(@PathVariable(name = "id") Long id) {
        return responseService.handleGetResponse(appointmentService.findBusyAppointmentsByPatient(id));
    }
}
