package com.unn.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.User;
import com.unn.repository.UserTypeRepo;
import com.unn.service.impl.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserTypeRepo userTypeRepo;

    private final Long userTypeId = UserTypes.PATIENT.getId();
    private final String username = "username";
    private final String password = "password";
    private final String mail = "mail";
    private final User user = new User(userTypeRepo.getOne(userTypeId), username, password, mail);

    @Override
    @Before
    public void setUp() {
        super.setUp();
//        userService.clearTable();
    }

    @After
    public void tearDown() {
//        userService.clearTable();
    }

    @Test
    public void getExistingUserByIdTest() throws Exception {
        Optional<User> retUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getMail())
        );

        String url = "/user/" + retUser.get().getId();
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        User createdUser = mapFromJson(jsonObj, User.class);
        assertEquals(createdUser.getUsername(), username);
        assertEquals(createdUser.getPassword(), password);
        assertEquals(createdUser.getMail(), mail);
    }

    @Test
    public void getNotExistingUserByIdTest() throws Exception {
        String url = "/user/0";
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusNOT_FOUND, status);
    }

    @Test
    public void getExistingUserByMailTest() throws Exception {
        Optional<User> retUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getMail())
        );

        String url = "/user/mail/" + retUser.get().getMail();
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        String jsonObj = mvcResult.getResponse().getContentAsString();
        User createdUser = mapFromJson(jsonObj, User.class);
        assertEquals(createdUser.getUsername(), username);
        assertEquals(createdUser.getPassword(), password);
        assertEquals(createdUser.getMail(), mail);
    }

    @Test
    public void getNotExistingUserByMailTest() throws Exception {
        String url = "/user/mail/no";
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusNOT_FOUND, status);
    }

    @Test
    public void deleteExistingUserByIdTest() throws Exception {
        Optional<User> retUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getMail())
        );

        Long userId = retUser.get().getId();
        String url = "/user/" + userId + "/delete";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        retUser = userService.findUser(userId);
        assertTrue(retUser.isEmpty());
    }

    @Test
    public void deleteNotExistingUserByIdTest() throws Exception {
        String url = "/user/0/delete";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusNOT_FOUND, status);
    }

    @Test
    public void deleteExistingUserByMailTest() throws Exception {
        Optional<User> retUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getMail())
        );

        String userMail = retUser.get().getMail();
        String url = "/user/mail/" + userMail + "/delete";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        retUser = userService.findUser(userMail);
        assertTrue(retUser.isEmpty());
    }

    @Test
    public void deleteNotExistingUserByMailTest() throws Exception {
        String url = "/user/mail/no/delete";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusNOT_FOUND, status);
    }

    @Test
    public void registrationTest() throws Exception {
        String url = "/user/registration";
        String jsonToSend = super.mapToJson(user);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonToSend))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        Optional<User> retUser = userService.findUser(user.getMail());
        assertEquals(retUser.get().getUsername(), username);
        assertEquals(retUser.get().getPassword(), password);
        assertEquals(retUser.get().getMail(), mail);
    }

    @Test
    public void registrationNotValidUserTest() throws Exception {
        String url = "/user/registration";
        User notValidUser = user;
        notValidUser.setType(null);
        String jsonToSend = super.mapToJson(notValidUser);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonToSend))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusBAD_REQUEST, status);
    }

    @Test
    public void editTest() throws Exception {
        Optional<User> retUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getMail())
        );
        final String usernameUpd = "usernameUpd";
        final String passwordUpd = "passwordUpd";
        User editUser = retUser.get();
        editUser.setUsername(usernameUpd);
        editUser.setPassword(passwordUpd);

        String url = "/user/edit";
        String jsonToSend = super.mapToJson(editUser);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonToSend))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusOK, status);

        Optional<User> retEditUser = userService.findUser(retUser.get().getId());
        assertEquals(retEditUser.get().getUsername(), usernameUpd);
        assertEquals(retEditUser.get().getPassword(), passwordUpd);
        assertEquals(retEditUser.get().getMail(), mail);
    }

    @Test
    public void editNotValidUserTest() throws Exception {
        Optional<User> retUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getMail())
        );
        final String usernameUpd = "usernameUpd";
        final String passwordUpd = "passwordUpd";
        final String mailUpd = "mailUpd";
        User editUser = retUser.get();
        editUser.setUsername(usernameUpd);
        editUser.setPassword(passwordUpd);
        editUser.setMail(mailUpd);

        String url = "/user/edit";
        String jsonToSend = super.mapToJson(editUser);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonToSend))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(statusBAD_REQUEST, status);
    }
}
