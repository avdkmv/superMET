package com.unn.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.unn.model.Appointment;
import com.unn.repository.AppointmentRepo;
import com.unn.service.IAppointmentService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    // private final ValidationService validation;

    private final AppointmentRepo appointmentRepo;

    // private final DoctorRepo doctorRepo;
    // private final PatientRepo patientRepo;

    // @Override
    // public Optional<Appointment> createAppointment(Long doctorId, Date date) {
    //     if (!validation.validateAppointmentCreation(doctorId)) {
    //         return Optional.empty();
    //     }

    //     Optional<Doctor> doctor = doctorRepo.findById(doctorId);
    //     Appointment appointment = new Appointment(doctor.get(), date);
    //     return Optional.of(appointment);
    // }

    @Override
    public Optional<Appointment> findAppointment(Long appointmentId) {
        return appointmentRepo.findById(appointmentId);
    }

    @Override
    public Optional<Appointment> findAppointment(Long doctorId, Long patientId) {
        return appointmentRepo.findByDoctorIdAndPatientId(doctorId, patientId);
    }

    @Override
    public Optional<Appointment> deleteAppointment(Long appointmentId) {
        Optional<Appointment> appointment = appointmentRepo.findById(appointmentId);
        if (appointment.isPresent()) appointmentRepo.deleteById(appointmentId);
        return appointment;
    }

    @Override
    public Optional<Appointment> deleteAppointment(Long doctorId, Long patientId) {
        Optional<Appointment> appointment = appointmentRepo.findByDoctorIdAndPatientId(doctorId, patientId);
        if (appointment.isPresent()) appointmentRepo.deleteByDoctorIdAndPatientId(doctorId, patientId);
        return appointment;
    }

    @Override
    public Optional<Appointment> updateAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
        return Optional.of(appointment);
    }

    @Override
    public Optional<Appointment> newResult(Long appointmentId, Long documentId) {
        // TODO:  implement method
        return null;
    }

    @Override
    public Optional<Appointment> newResult(Long appointmentId, Long doctorId, Long patientId) {
        // TODO:  implement method
        return null;
    }

    @Override
    public Optional<List<Appointment>> findBusyAppointmentsByDoctor(Long doctorId) {
        return appointmentRepo.findAllByDoctorIdAndBusy(doctorId, true);
    }

    @Override
    public Optional<List<Appointment>> findFreeAppointmentsByDoctor(Long doctorId) {
        return appointmentRepo.findAllByDoctorIdAndBusy(doctorId, false);
    }

    @Override
    public Optional<List<Appointment>> findBusyAppointmentsByPatient(Long patientId) {
        return appointmentRepo.findAllByPatientIdAndBusy(patientId, true);
    }

    @Override
    public Optional<List<Appointment>> findFreeAppointmentsByPatient(Long patientId) {
        return appointmentRepo.findAllByPatientIdAndBusy(patientId, false);
    }

    @Override
    public Optional<List<Appointment>> findFreeAppointmentsByDay(Long day, Long doctorId) {
        Optional<List<Appointment>> appointments = findAppointmentsByDay(day, doctorId);
        List<Appointment> freeAppointments = new ArrayList<Appointment>();

        appointments.ifPresent(
            list ->
                list.forEach(
                    a -> {
                        final Calendar calendar = Calendar.getInstance();
                        calendar.setTime(a.getDate());

                        if (!a.isBusy()) {
                            freeAppointments.add(a);
                        }
                    }
                )
        );

        return Optional.of(freeAppointments);
    }

    public Optional<List<Appointment>> findAppointmentsByDay(Long day, Long doctorId) {
        Optional<List<Appointment>> total = appointmentRepo.findAllByDoctorId(doctorId);
        List<Appointment> result = new ArrayList<>();

        total.ifPresent(
            list ->
                list.forEach(
                    a -> {
                        final Calendar calendar = Calendar.getInstance();
                        calendar.setTime(a.getDate());

                        if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                            result.add(a);
                        }
                    }
                )
        );

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<String> countFreeAppointmetnsPerDay(Long day, Long doctorId) {
        String ratio;
        Optional<List<Appointment>> allAppointments = findAppointmentsByDay(day, doctorId);
        Optional<List<Appointment>> freeAppointments = findFreeAppointmentsByDay(day, doctorId);

        if (allAppointments.isEmpty()) {
            return Optional.empty();
        }

        if (freeAppointments.isEmpty()) {
            ratio = "0/" + allAppointments.get().size();
        } else {
            ratio = freeAppointments.get().size() + "/" + allAppointments.get().size();
        }
        return Optional.of(ratio);
    }

    public Optional<Appointment> makeAppointment(Long id) {
        Optional<Appointment> appointment = findAppointment(id);

        appointment.ifPresent(
            a -> {
                a.setBusy(true);
                a.setCode(UUID.randomUUID().toString());
                appointmentRepo.save(appointment.get());
            }
        );

        return appointment;
    }

    @Override
    public void clearTable() {
        appointmentRepo.deleteAll();
    }

}
