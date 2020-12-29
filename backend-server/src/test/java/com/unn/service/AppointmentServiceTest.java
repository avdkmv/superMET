package com.unn.service;

import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.Appointment;
import com.unn.model.User;
import com.unn.service.impl.AppointmentService;
import com.unn.service.impl.CalendarService;
import com.unn.service.impl.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentServiceTest {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CalendarService calendarService;

    private final String username = "username";
    private final String password = "password";
    private final String mail = "mail";
    Optional<User> doctor = Optional.empty();

    @Before
    public void setUp() {
        // TODO: fix cascade delete
        appointmentService.clearTable();
        calendarService.clearTable();
        userService.clearUserTable();
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        doctor = userService.createUser(signupRequest);
    }

    @After
    public void tearDown() {
        // TODO: fix cascade delete
        appointmentService.clearTable();
        calendarService.clearTable();
        userService.clearUserTable();
        doctor = Optional.empty();
    }

    @Test
    public void findAppointmentByIdTest() {
        List<Appointment> allApp = appointmentService.findAll().get();
        Appointment app = allApp.get(0);

        Optional<Appointment> retApp = appointmentService.findAppointment(app.getId());

        assertEquals(app.getDate(), retApp.get().getDate());
        assertEquals(app.getDoctor().getId(), retApp.get().getDoctor().getId());
        assertEquals(app.getCalendar().getId(), retApp.get().getCalendar().getId());
    }

    @Test
    public void updateAppointmentTest() {
        List<Appointment> allApp = appointmentService.findAll().get();
        Appointment app = allApp.get(0);
        app.setBusy(true);
        appointmentService.updateAppointment(app);

        Optional<Appointment> retApp = appointmentService.findAppointment(app.getId());

        assertEquals(app.getDate(), retApp.get().getDate());
        assertEquals(app.getDoctor().getId(), retApp.get().getDoctor().getId());
        assertEquals(app.getCalendar().getId(), retApp.get().getCalendar().getId());
        assertEquals(app.isBusy(), retApp.get().isBusy());
    }

    @Test
    public void findFreeAppointmentsByDoctorTest() {
        List<Appointment> allApp = appointmentService.findAll().get();
        for (int i = 1; i < allApp.size(); i++) {
            allApp.get(i).setBusy(true);
            appointmentService.updateAppointment(allApp.get(i));
        }

        Optional<List<Appointment>> retApp = appointmentService.findFreeAppointmentsByDoctor(doctor.get().getId());
        assertEquals(1, retApp.get().size());

        assertEquals(allApp.get(0).getDate(), retApp.get().get(0).getDate());
        assertEquals(allApp.get(0).getDoctor().getId(), retApp.get().get(0).getDoctor().getId());
        assertEquals(allApp.get(0).getCalendar().getId(), retApp.get().get(0).getCalendar().getId());
        assertEquals(allApp.get(0).isBusy(), retApp.get().get(0).isBusy());
    }

    @Test
    public void findBusyAppointmentsByDoctorTest() {
        List<Appointment> allApp = appointmentService.findAll().get();
        allApp.get(0).setBusy(true);
        appointmentService.updateAppointment(allApp.get(0));

        Optional<List<Appointment>> retApp = appointmentService.findBusyAppointmentsByDoctor(doctor.get().getId());
        assertEquals(1, retApp.get().size());

        assertEquals(allApp.get(0).getDate(), retApp.get().get(0).getDate());
        assertEquals(allApp.get(0).getDoctor().getId(), retApp.get().get(0).getDoctor().getId());
        assertEquals(allApp.get(0).getCalendar().getId(), retApp.get().get(0).getCalendar().getId());
        assertEquals(allApp.get(0).isBusy(), retApp.get().get(0).isBusy());
    }

    @Test
    public void findFreeAppointmentsByDayTest() {
        List<Appointment> allApp = appointmentService.findAll().get();
        for (int i = 1; i < allApp.size(); i++) {
            allApp.get(i).setBusy(true);
            appointmentService.updateAppointment(allApp.get(i));
        }

        Optional<List<Appointment>> retApp = appointmentService.findFreeAppointmentsByDay(Long.valueOf(allApp.get(0).getDate().getDate()),
                                                                                          doctor.get().getId());
        assertEquals(1, retApp.get().size());

        assertEquals(allApp.get(0).getDate(), retApp.get().get(0).getDate());
        assertEquals(allApp.get(0).getDoctor().getId(), retApp.get().get(0).getDoctor().getId());
        assertEquals(allApp.get(0).getCalendar().getId(), retApp.get().get(0).getCalendar().getId());
        assertEquals(allApp.get(0).isBusy(), retApp.get().get(0).isBusy());
    }

    @Test
    public void findAppointmentsByDayTest() {
        List<Appointment> allApp = appointmentService.findAll().get();
        List<Appointment> dateApp = new ArrayList<Appointment>();
        Date currDate = allApp.get(0).getDate();
        for (int i = 0; i < allApp.size(); i++) {
            if (allApp.get(i).getDate().getDate() == currDate.getDate()) {
                dateApp.add(allApp.get(i));
            }
        }

        Optional<List<Appointment>> retApp = appointmentService.findAppointmentsByDay(Long.valueOf(currDate.getDate()),
                                                                                      doctor.get().getId());
        assertEquals(dateApp.size(), retApp.get().size());

        for (int i = 0; i < dateApp.size(); i++) {
            assertEquals(dateApp.get(i).getDate(), retApp.get().get(i).getDate());
            assertEquals(dateApp.get(i).getDoctor().getId(), retApp.get().get(i).getDoctor().getId());
            assertEquals(dateApp.get(i).getCalendar().getId(), retApp.get().get(i).getCalendar().getId());
            assertEquals(dateApp.get(i).isBusy(), retApp.get().get(i).isBusy());
        }
    }

    @Test
    public void countFreeAppointmetnsPerDayTest() {
        List<Appointment> allApp = appointmentService.findAll().get();
        Date currDate = allApp.get(0).getDate();
        Long dateCount = 0L;
        for (int i = 0; i < 3; i++) {
            if (allApp.get(i).getDate().getDate() == currDate.getDate()) {
                dateCount++;
            }
        }
        for (int i = 3; i < allApp.size(); i++) {
            if (allApp.get(i).getDate().getDate() == currDate.getDate()) {
                dateCount++;
            }
            allApp.get(i).setBusy(true);
            appointmentService.updateAppointment(allApp.get(i));
        }

        Optional<String> retApp = appointmentService.countFreeAppointmetnsPerDay(Long.valueOf(currDate.getDate()),
                                                                                            doctor.get().getId());
        assertEquals("3/" + dateCount, retApp.get());
    }

}
