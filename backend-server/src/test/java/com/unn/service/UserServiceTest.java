package com.unn.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.*;
import com.unn.repository.FacilityRepo;
import com.unn.service.impl.AppointmentService;
import com.unn.service.impl.CalendarService;
import com.unn.service.impl.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private FacilityRepo facilityRepo;

    private final String username = "username";
    private final String password = "password";
    private final String mail = "mail";

    @Before
    @After
    public void clear() {
        // TODO: fix cascade delete
        appointmentService.clearTable();
        calendarService.clearTable();
        facilityRepo.deleteAll();
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

    @Test
    public void createPatientTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        Optional<User> dbUser = userService.findUser(id);

        assertEquals(username, dbUser.get().getUsername());
        assertEquals(password, dbUser.get().getPassword());
        assertEquals(mail, dbUser.get().getMail());
        assertEquals((Long)UserTypes.PATIENT.getId(), dbUser.get().getType().getId());
    }

    @Test
    public void findByIdDoctorTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        userService.createUser(signupRequest);

        Optional<User> dbUser = userService.findUser(mail);

        assertEquals(username, dbUser.get().getUsername());
        assertEquals(password, dbUser.get().getPassword());
        assertEquals(mail, dbUser.get().getMail());
        assertEquals((Long)UserTypes.DOCTOR.getId(), dbUser.get().getType().getId());
    }

    @Test
    public void findByIdPatientTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        userService.createUser(signupRequest);

        Optional<User> dbUser = userService.findUser(mail);

        assertEquals(username, dbUser.get().getUsername());
        assertEquals(password, dbUser.get().getPassword());
        assertEquals(mail, dbUser.get().getMail());
        assertEquals((Long)UserTypes.PATIENT.getId(), dbUser.get().getType().getId());
    }

    // TODO: fix cascade delete
    @Ignore
    @Test
    public void deleteByIdDoctorTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        userService.deleteUser(id);
        Optional<User> dbUser = userService.findUser(id);
        assertTrue(dbUser.isEmpty());
    }

    @Test
    public void deleteByIdPatientTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        userService.deleteUser(id);
        Optional<User> dbUser = userService.findUser(id);
        assertTrue(dbUser.isEmpty());
    }

    // TODO: fix cascade delete
    @Ignore
    @Test
    public void deleteByMailDoctorTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        String m = createdUser.get().getMail();

        userService.deleteUser(m);
        Optional<User> dbUser = userService.findUser(m);
        assertTrue(dbUser.isEmpty());
    }

    @Test
    public void deleteByMailPatientTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        String m = createdUser.get().getMail();

        userService.deleteUser(m);
        Optional<User> dbUser = userService.findUser(m);
        assertTrue(dbUser.isEmpty());
    }

    @Test
    public void updateDoctorTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        User createdUser = userService.createUser(signupRequest).get();
        Long id = createdUser.getId();

        final String usernameNew = "username1";
        final String passwordNew = "password1";
        createdUser.setUsername(usernameNew);
        createdUser.setPassword(passwordNew);
        userService.updateUser(createdUser);

        Optional<User> dbUser = userService.findUser(id);

        assertEquals(usernameNew, dbUser.get().getUsername());
        assertEquals(passwordNew, dbUser.get().getPassword());
        assertEquals(mail, dbUser.get().getMail());
        assertEquals((Long)UserTypes.DOCTOR.getId(), dbUser.get().getType().getId());
    }

    @Test
    public void updatePatientTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        User createdUser = userService.createUser(signupRequest).get();
        Long id = createdUser.getId();

        final String usernameNew = "username1";
        final String passwordNew = "password1";
        createdUser.setUsername(usernameNew);
        createdUser.setPassword(passwordNew);
        userService.updateUser(createdUser);

        Optional<User> dbUser = userService.findUser(id);

        assertEquals(usernameNew, dbUser.get().getUsername());
        assertEquals(passwordNew, dbUser.get().getPassword());
        assertEquals(mail, dbUser.get().getMail());
        assertEquals((Long)UserTypes.PATIENT.getId(), dbUser.get().getType().getId());
    }

    @Test
    public void getAllDoctorsTest() {
        for (int i = 0; i < 3; i++) {
            SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username + i,
                    password + i, mail + i);
            userService.createUser(signupRequest);
        }

        List<User> allPatient = userService.getAllByType(UserTypes.DOCTOR).get();
        assertEquals(3, allPatient.size());

        for (int i = 0; i < allPatient.size(); i++) {
            assertEquals(username + i, allPatient.get(i).getUsername());
            assertEquals(password + i, allPatient.get(i).getPassword());
            assertEquals(mail + i, allPatient.get(i).getMail());
            assertEquals((Long)UserTypes.DOCTOR.getId(), allPatient.get(i).getType().getId());
        }
    }

    @Test
    public void getAllPatientsTest() {
        for (int i = 0; i < 3; i++) {
            SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username + i,
                                                    password + i, mail + i);
            userService.createUser(signupRequest);
        }

        List<User> allPatient = userService.getAllByType(UserTypes.PATIENT).get();
        assertEquals(3, allPatient.size());

        for (int i = 0; i < allPatient.size(); i++) {
            assertEquals(username + i, allPatient.get(i).getUsername());
            assertEquals(password + i, allPatient.get(i).getPassword());
            assertEquals(mail + i, allPatient.get(i).getMail());
            assertEquals((Long)UserTypes.PATIENT.getId(), allPatient.get(i).getType().getId());
        }
    }

    @Test
    public void getUserAsDoctorTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        Optional<Doctor> dbDoctor = userService.getDoctor(id);

        assertEquals(username, dbDoctor.get().getUsername());
        assertEquals(password, dbDoctor.get().getPassword());
        assertEquals(mail, dbDoctor.get().getMail());
        assertEquals((Long)UserTypes.DOCTOR.getId(), dbDoctor.get().getType().getId());
    }

    @Test
    public void getUserAsPatientTest() {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        Optional<Patient> dbPatient = userService.getPatient(id);

        assertEquals(username, dbPatient.get().getUsername());
        assertEquals(password, dbPatient.get().getPassword());
        assertEquals(mail, dbPatient.get().getMail());
        assertEquals((Long)UserTypes.PATIENT.getId(), dbPatient.get().getType().getId());
    }

    @Test
    public void attachDoctorToFacilityTest() {
        Facility facility = new Facility("facility", "there_is_only_covid");
        Long facilityId = facilityRepo.save(facility).getId();

        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long userId = createdUser.get().getId();

        userService.attachDoctorToFacility(userId, facilityId);
        Optional<Doctor> dbDoctor = userService.getDoctor(userId);

        assertEquals(username, dbDoctor.get().getUsername());
        assertEquals(password, dbDoctor.get().getPassword());
        assertEquals(mail, dbDoctor.get().getMail());
        assertEquals(facilityId, dbDoctor.get().getFacility().getId());
        assertEquals((Long)UserTypes.DOCTOR.getId(), dbDoctor.get().getType().getId());
    }

}
