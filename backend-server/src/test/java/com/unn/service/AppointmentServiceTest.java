package com.unn.service;

import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.Calendar;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Before
    @After
    public void clear() {
        // TODO: fix cascade delete
        appointmentService.clearTable();
        calendarService.clearTable();
        userService.clearUserTable();
    }

    @Test
    public void createDoctorTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        Optional<User> dbUser = userService.findUser(id);
        Optional<Calendar> dbCalendar = calendarService.findCalendarByDoctorId(id);
        assertTrue(dbCalendar.isPresent());

        assertEquals(username, dbUser.get().getUsername());
        assertEquals(password, dbUser.get().getPassword());
        assertEquals(mail, dbUser.get().getMail());
        assertEquals((Long)UserTypes.DOCTOR.getId(), dbUser.get().getType().getId());
    }
}
