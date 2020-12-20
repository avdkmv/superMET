package com.unn.service;

import com.unn.model.Appointment;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    Optional<Appointment> createAppointment(Long doctorId, Date date);

    Optional<Appointment> findAppointment(Long appointmentId);
    Optional<Appointment> findAppointment(Long doctorId, Long patientId);

    Optional<List<Appointment>> findAllBusyDoctorAppointment(Long doctorId);
    Optional<List<Appointment>> findAllFreeDoctorAppointment(Long doctorId);

    Optional<List<Appointment>> findAllBusyPatientAppointment(Long patientId);
    Optional<List<Appointment>> findAllFreePatientAppointment(Long patientId);

    Optional<List<Appointment>> findAllFreeAppointmentsByDay(Long day);
    Optional<String> getRatioFreeAllByDay(Long day);

    Optional<Appointment> updateAppointment(Appointment appointment);

    Optional<Appointment> deleteAppointment(Long appointmentId);
    Optional<Appointment> deleteAppointment(Long doctorId, Long patientId);

    Optional<Appointment> newResult(Long appointmentId, Long documentId);
    Optional<Appointment> newResult(Long appointmentId, Long doctorId, Long patientId);
}
