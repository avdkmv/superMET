package com.unn.controller;

import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.Appointment;
import com.unn.model.User;
import com.unn.service.impl.AppointmentService;
import com.unn.service.impl.CalendarService;
import com.unn.service.impl.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentControllerTest extends AbstractControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CalendarService calendarService;

    private final String username = "username";
    private final String password = "password";
    private final String mail = "mail";
    Optional<User> doctor = Optional.empty();

    @Override
    @Before
    public void setUp() {
        super.setUp();
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
    public void createAppointmentByIdTest() throws Exception {
        List<Appointment> allApp = appointmentService.findAll().get();
        Appointment app = allApp.get(0);

        String url = "/appointment/create/" + app.getId();
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        Optional<Appointment> retApp = appointmentService.findAppointment(app.getId());

        assertEquals(true, retApp.get().isBusy());
        assertNotNull(retApp.get().getCode());
        assertEquals(doctor.get().getId(), retApp.get().getDoctor().getId());
    }

    @Test
    public void getAppointmentByIdTest() throws Exception {
        List<Appointment> allApp = appointmentService.findAll().get();
        Appointment app = allApp.get(0);

        String url = "/appointment/" + app.getId();
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        JSONObject responseApp = new JSONObject(jsonObj);

        assertEquals(app.isBusy(), responseApp.getBoolean("busy"));
        assertEquals(app.getDoctor().getId(), (Long)(((JSONObject)responseApp.get("doctor")).getLong("id")));
        String [] retDate = responseApp.getString("date").split("-");

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(app.getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        assertEquals(Long.valueOf(year), Long.valueOf(retDate[0]));
        assertEquals(Long.valueOf(month), Long.valueOf(retDate[1]));
        String dayRet = retDate[2].split("T")[0];
        assertEquals(Long.valueOf(day) ,Long.valueOf(dayRet));
    }

    @Test
    public void getFreeDoctorsByIdTest() throws Exception {
        List<Appointment> allApp = appointmentService.findAll().get();
        for (int i = 1; i < allApp.size(); i++) {
            allApp.get(i).setBusy(true);
            appointmentService.updateAppointment(allApp.get(i));
        }

        String url = "/appointment/doctor/" + doctor.get().getId() + "/free";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        JSONArray responseApp = new JSONArray(jsonObj);
        assertEquals(1, responseApp.length());

        assertEquals(allApp.get(0).isBusy(), ((JSONObject)responseApp.get(0)).getBoolean("busy"));
        assertEquals(allApp.get(0).getDoctor().getId(), (Long)(((JSONObject)((JSONObject)responseApp.get(0))
                                                            .get("doctor")).getLong("id")));
        String [] retDate = ((JSONObject)responseApp.get(0)).getString("date").split("-");

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(allApp.get(0).getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        assertEquals(Long.valueOf(year), Long.valueOf(retDate[0]));
        assertEquals(Long.valueOf(month), Long.valueOf(retDate[1]));
        String dayRet = retDate[2].split("T")[0];
        assertEquals(Long.valueOf(day) ,Long.valueOf(dayRet));
    }

    @Test
    public void getBusyDoctorsByIdTest() throws Exception {
        List<Appointment> allApp = appointmentService.findAll().get();
        allApp.get(0).setBusy(true);
        appointmentService.updateAppointment(allApp.get(0));

        String url = "/appointment/doctor/" + doctor.get().getId() + "/busy";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        JSONArray responseApp = new JSONArray(jsonObj);
        assertEquals(1, responseApp.length());

        assertEquals(allApp.get(0).isBusy(), ((JSONObject)responseApp.get(0)).getBoolean("busy"));
        assertEquals(allApp.get(0).getDoctor().getId(), (Long)(((JSONObject)((JSONObject)responseApp.get(0))
                                                                    .get("doctor")).getLong("id")));
        String [] retDate = ((JSONObject)responseApp.get(0)).getString("date").split("-");

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(allApp.get(0).getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        assertEquals(Long.valueOf(year), Long.valueOf(retDate[0]));
        assertEquals(Long.valueOf(month), Long.valueOf(retDate[1]));
        String dayRet = retDate[2].split("T")[0];
        assertEquals(Long.valueOf(day) ,Long.valueOf(dayRet));
    }

    @Test
    public void getFreeDoctorsByDayTest() throws Exception {
        List<Appointment> allApp = appointmentService.findAll().get();
        for (int i = 1; i < allApp.size(); i++) {
            allApp.get(i).setBusy(true);
            appointmentService.updateAppointment(allApp.get(i));
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(allApp.get(0).getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String url = "/appointment/doctor/" + doctor.get().getId() + "/free/" + day;
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        JSONArray responseApp = new JSONArray(jsonObj);
        assertEquals(1, responseApp.length());

        assertEquals(allApp.get(0).isBusy(), ((JSONObject)responseApp.get(0)).getBoolean("busy"));
        assertEquals(allApp.get(0).getDoctor().getId(), (Long)(((JSONObject)((JSONObject)responseApp.get(0))
                .get("doctor")).getLong("id")));
        String [] retDate = ((JSONObject)responseApp.get(0))
                .getString("date").split("-");

        assertEquals(Long.valueOf(year), Long.valueOf(retDate[0]));
        assertEquals(Long.valueOf(month), Long.valueOf(retDate[1]));
        String dayRet = retDate[2].split("T")[0];
        assertEquals(Long.valueOf(day) ,Long.valueOf(dayRet));
    }

    @Test
    public void getRatioFreeDaysTest() throws Exception {
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

        String url = "/appointment/doctor/" + doctor.get().getId() + "/free/" + allApp.get(0).getDate().getDate() + "/ratio";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();

        assertEquals("3/" + dateCount, jsonObj);
    }

}
