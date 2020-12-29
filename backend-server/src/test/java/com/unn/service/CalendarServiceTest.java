package com.unn.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.Calendar;
import com.unn.model.Doctor;
import com.unn.model.User;
import com.unn.repository.DoctorRepo;
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
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CalendarServiceTest {
    @Autowired
    private CalendarService calendarService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    DoctorRepo doctorRepo;

    private final SignupRequest REQUEST = new SignupRequest(UserTypes.DOCTOR.getId(), "username", "password", "mail");

    @Before
    @After
    public void clear() {
        userService.clearTable();
        calendarService.clearTable();
        appointmentService.clearTable();
    }

    @Test
    public void createCalendarByDoctorIdTest() {
        User user = userService.createUser(REQUEST).get();
        Doctor doctor = doctorRepo.findById(user.getId()).get();
        Optional<Calendar> calendarFromRepo = calendarService.findCalendar(doctor.getCalendar().getId());

        assertTrue(calendarFromRepo.isPresent());
    }

    @Test
    public void findCalendarTest() {
        User user = userService.createUser(REQUEST).get();
        Doctor doctor = doctorRepo.findById(user.getId()).get();
        Optional<Calendar> calendarFromRepo = calendarService.findCalendar(doctor.getCalendar().getId());

        assertTrue(calendarFromRepo.isPresent());
        assertEquals(doctor.getCalendar(), calendarFromRepo.get());
    }

    @Test
    public void deleteCalendarTest() {
        User user = userService.createUser(REQUEST).get();
        Doctor doctor = doctorRepo.findById(user.getId()).get();
        calendarService.deleteCalendar(doctor.getCalendar().getId());

        Optional<Calendar> calendarFromRepo = calendarService.findCalendar(doctor.getCalendar().getId());
        assertTrue(calendarFromRepo.isEmpty());
    }
}
