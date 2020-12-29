package com.unn.service.impl;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.unn.dto.DayResponse;
import com.unn.model.Appointment;
import com.unn.model.NotificationEmail;
import com.unn.model.Patient;
import com.unn.model.User;
import com.unn.repository.AppointmentRepo;
import com.unn.service.IAppointmentService;
import com.unn.util.AuthUtils;
import com.unn.util.RunnableSendEmail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final JavaMailSender sender;
    private final TaskScheduler taskScheduler;
    private final UserService users;

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

    public Optional<Appointment> terminate(Long id, Authentication auth) {
        if (AuthUtils.notAuthenticated(auth)) {
            return Optional.empty();
        }

        User user = (User) auth.getPrincipal();
        Optional<Appointment> appointment = appointmentRepo.findById(id);
        appointment.ifPresent(
            a -> {
                if (a.getPatient().getId() == user.getId() || a.getDoctor().getId() == user.getId()) {
                    a.setBusy(false);
                    a.setPatient(null);
                    appointmentRepo.save(a);
                }
            }
        );

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

    public Optional<Map<Integer, List<DayResponse>>> findFreeDaysByDoctor(Long doctorId) {
        Optional<List<Appointment>> freeAppointments = findFreeAppointmentsByDoctor(doctorId);
        if (freeAppointments.isEmpty()) {
            return Optional.empty();
        }

        Map<Integer, List<DayResponse>> result = new HashMap<>();
        freeAppointments
            .get()
            .forEach(
                a -> {
                    int day = a.getDate().getDayOfMonth();
                    int month = a.getDate().getMonthValue();

                    DayResponse dayResponse = new DayResponse();
                    dayResponse.setDay((long) day);

                    if (!result.containsKey(month)) {
                        Optional<String> ratio = countFreeAppointmetnsPerDay((long) day, doctorId);
                        ratio.ifPresent(r -> dayResponse.setRatio(r));

                        List<DayResponse> days = new ArrayList<>();
                        days.add(dayResponse);

                        result.put(month, days);
                    } else {
                        List<DayResponse> days = result.get(month);
                        if (!days.stream().anyMatch(d -> d.getDay() == day)) {
                            Optional<String> ratio = countFreeAppointmetnsPerDay((long) day, doctorId);
                            ratio.ifPresent(r -> dayResponse.setRatio(r));

                            days.add(dayResponse);
                        }
                    }
                }
            );

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
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
    public Optional<List<Appointment>> findBusyAppointmentsByPatient(Authentication auth) {
        return findAppointmentsByPatient(auth, true);
    }

    @Override
    public Optional<List<Appointment>> findFreeAppointmentsByPatient(Authentication auth) {
        return findAppointmentsByPatient(auth, false);
    }

    private Optional<List<Appointment>> findAppointmentsByPatient(Authentication auth, boolean busy) {
        if (AuthUtils.notAuthenticated(auth)) {
            return Optional.empty();
        }

        User user = (User) auth.getPrincipal();
        return appointmentRepo.findAllByPatientIdAndBusy(user.getId(), busy);
    }

    @Override
    public Optional<List<Appointment>> findFreeAppointmentsByDay(Long day, Long doctorId) {
        Optional<List<Appointment>> appointments = findAppointmentsByDay(day, doctorId);
        List<Appointment> freeAppointments = new ArrayList<Appointment>();

        appointments.ifPresent(
            list ->
                list.forEach(
                    a -> {
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
                        if (a.getDate().getDayOfMonth() == day) {
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

    public Optional<Appointment> makeAppointment(Long id, Authentication auth) {
        Optional<Appointment> appointment = findAppointment(id);
        Optional<Patient> user = Optional.empty();
        if (auth != null && auth.getName() != null) {
            user = users.getPatient(auth.getName());
        }

        user.ifPresent(
            u -> {
                appointment.ifPresent(
                    a -> {
                        a.setBusy(true);
                        a.setCode(UUID.randomUUID().toString());
                        a.setPatient(u);
                        appointmentRepo.save(appointment.get());

                        if (appointment.get().getPatient() != null) {
                            NotificationEmail email = new NotificationEmail(
                                appointment.get().getPatient().getEmail(),
                                "Appointment notification",
                                appointment.get().getDoctor().getUsername(),
                                appointment.get().getDate()
                            );
                            taskScheduler.schedule(
                                new RunnableSendEmail(email, sender),
                                Date.from(appointment.get().getDate().atZone(ZoneId.systemDefault()).toInstant())
                            );
                        }
                    }
                );
            }
        );

        return user.isPresent() ? appointment : Optional.empty();
    }
}
