package com.unn.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.Doctor;
import com.unn.model.Facility;
import com.unn.model.Patient;
import com.unn.model.User;
import com.unn.repository.FacilityRepo;
import com.unn.repository.UserTypeRepo;
import com.unn.service.impl.AppointmentService;
import com.unn.service.impl.CalendarService;
import com.unn.service.impl.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UserControllerTest extends AbstractControllerTest {
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

    @Override
    @Before
    public void setUp() {
        super.setUp();
        // TODO: fix cascade delete
        appointmentService.clearTable();
        calendarService.clearTable();
        facilityRepo.deleteAll();
        userService.clearUserTable();
        userService.clearDoctorTable();
        userService.clearPatientTable();
    }

    @After
    public void tearDown() {
        appointmentService.clearTable();
        calendarService.clearTable();
        facilityRepo.deleteAll();
        userService.clearUserTable();
        userService.clearDoctorTable();
        userService.clearPatientTable();
    }

    @Test
    public void getExistingDoctorByIdTest() throws Exception {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        String url = "/user/doctor/" + id;
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        JSONObject responseDoctor = new JSONObject(jsonObj);

        assertEquals(username, responseDoctor.getString("username"));
        assertEquals(password, responseDoctor.getString("password"));
        assertEquals(mail, responseDoctor.getString("mail"));
        assertEquals(UserTypes.DOCTOR.getId(), (Long)((JSONObject)responseDoctor.get("type")).getLong("id"));
    }

    @Test
    public void getNotExistingDoctorByIdTest() throws Exception {
        String url = "/user/doctor/0";
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusNOT_FOUND, status);
    }

    @Test
    public void getExistingPatientByIdTest() throws Exception {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        String url = "/user/patient/" + id;
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        JSONObject responsePatients = new JSONObject(jsonObj);

        assertEquals(username, responsePatients.getString("username"));
        assertEquals(password, responsePatients.getString("password"));
        assertEquals(mail, responsePatients.getString("mail"));
        assertEquals(UserTypes.PATIENT.getId(), (Long)((JSONObject)responsePatients.get("type")).getLong("id"));
    }

    @Test
    public void getNotExistingPatientByIdTest() throws Exception {
        String url = "/user/patient/0";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusNOT_FOUND, status);
    }

    @Test
    public void signupDoctorTest() throws Exception {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        String url = "/user/signup";
        String jsonToSend = super.mapToJson(signupRequest);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonToSend))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusCREATED, status);

        Optional<User> retUser = userService.findUser(mail);
        assertEquals(username, retUser.get().getUsername());
        assertEquals(password, retUser.get().getPassword());
        assertEquals(mail, retUser.get().getMail());
        assertEquals(UserTypes.DOCTOR.getId(), retUser.get().getType().getId());
    }

    @Test
    public void signupPatientTest() throws Exception {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        String url = "/user/signup";
        String jsonToSend = super.mapToJson(signupRequest);
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonToSend))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusCREATED, status);

        Optional<User> retUser = userService.findUser(mail);
        assertEquals(username, retUser.get().getUsername());
        assertEquals(password, retUser.get().getPassword());
        assertEquals(mail, retUser.get().getMail());
        assertEquals(UserTypes.PATIENT.getId(), retUser.get().getType().getId());
    }

    @Ignore
    @Test
    public void deleteExistingDoctorByIdTest() throws Exception {
        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        String url = "/user/" + id + "/delete";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        Optional<User> dbUser = userService.findUser(id);
        assertTrue(dbUser.isEmpty());
    }

    @Test
    public void deleteExistingPatientByIdTest() throws Exception {
        SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long id = createdUser.get().getId();

        String url = "/user/" + id + "/delete";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        Optional<User> dbUser = userService.findUser(id);
        assertTrue(dbUser.isEmpty());
    }

    @Test
    public void deleteNotExistingUserByIdTest() throws Exception {
        String url = "/user/0/delete";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusNOT_FOUND, status);
    }

    @Test
    public void getAllPatientsTest() throws Exception {
        for (int i = 0; i < 3; i++) {
            SignupRequest signupRequest = new SignupRequest(UserTypes.PATIENT.getId(), username + i,
                    password + i, mail + i);
            userService.createUser(signupRequest);
        }

        String url = "/user/patients";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        JSONArray responsePatients = new JSONArray(jsonObj);

        for (int i = 0; i < responsePatients.length(); i++) {
            assertEquals(username + i, ((JSONObject)responsePatients.get(i)).getString("username"));
            assertEquals(password + i, ((JSONObject)responsePatients.get(i)).getString("password"));
            assertEquals(mail + i, ((JSONObject)responsePatients.get(i)).getString("mail"));
            assertEquals(UserTypes.PATIENT.getId(), (Long)((JSONObject)((JSONObject)responsePatients.get(i)).get("type")).getLong("id"));
        }
    }

    @Test
    public void attachDoctorToFacilityTest() throws Exception {
        Facility facility = new Facility("facility", "there_is_only_covid");
        Long facilityId = facilityRepo.save(facility).getId();

        SignupRequest signupRequest = new SignupRequest(UserTypes.DOCTOR.getId(), username, password, mail);
        Optional<User> createdUser = userService.createUser(signupRequest);
        Long userId = createdUser.get().getId();

        String url = "/user/doctor/" + userId + "/facility/" + facilityId;
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusCREATED, status);

        Optional<Doctor> dbDoctor = userService.getDoctor(userId);

        assertEquals(username, dbDoctor.get().getUsername());
        assertEquals(password, dbDoctor.get().getPassword());
        assertEquals(mail, dbDoctor.get().getMail());
        assertEquals(facilityId, dbDoctor.get().getFacility().getId());
        assertEquals(UserTypes.DOCTOR.getId(), dbDoctor.get().getType().getId());
    }

}
