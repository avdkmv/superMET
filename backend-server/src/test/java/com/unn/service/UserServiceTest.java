package com.unn.service;

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
    private UserTypeRepo userTypeRepo;

    private final Long userTypeId = UserTypes.PATIENT.getId();
    private final String username = "username";
    private final String password = "password";
    private final String mail = "mail";
    private final User user = new User(userTypeRepo.getOne(userTypeId), username, password, mail);

    @Before
    @After
    public void clear() {
        userService.clearTable();
    }

    @Test
    public void createUserTest() {
        Optional<User> createdUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getEmail())
        );
        assertEquals(createdUser.get().getUsername(), username);
        assertEquals(createdUser.get().getPassword(), password);
        assertEquals(createdUser.get().getEmail(), mail);
    }

    @Test
    public void findUserByIdTest() {
        Optional<User> createdUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getEmail())
        );
        Optional<User> user = userService.findUser(createdUser.get().getId());
        assertEquals(user.get().getUsername(), username);
        assertEquals(user.get().getPassword(), password);
        assertEquals(user.get().getEmail(), mail);
    }

    @Test
    public void findUserByMailTest() {
        Optional<User> createdUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getEmail())
        );
        Optional<User> user = userService.findUser(createdUser.get().getEmail());
        assertEquals(user.get().getUsername(), username);
        assertEquals(user.get().getPassword(), password);
        assertEquals(user.get().getEmail(), mail);
    }

    @Test
    public void deleteUserByIdTest() {
        Optional<User> createdUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getEmail())
        );
        userService.deleteUser(createdUser.get().getId());
        Optional<User> user = userService.findUser(mail);
        assertTrue(user.isEmpty());
    }

    @Test
    public void deleteUserByMailTest() {
        Optional<User> createdUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getEmail())
        );
        userService.deleteUser(createdUser.get().getEmail());
        Optional<User> user = userService.findUser(mail);
        assertTrue(user.isEmpty());
    }

    @Test
    public void updateUserByMailTest() {
        final String usernameNew = "username1";
        final String passwordNew = "password1";

        Optional<User> createdUser = userService.createUser(
            new SignupRequest(user.getType().getId(), user.getUsername(), user.getPassword(), user.getEmail())
        );
        User modifyUser = createdUser.get();
        modifyUser.setUsername(usernameNew);
        modifyUser.setPassword(passwordNew);
        userService.updateUser(modifyUser);
        Optional<User> user = userService.findUser(mail);
        assertEquals(user.get().getUsername(), usernameNew);
        assertEquals(user.get().getPassword(), passwordNew);
        assertEquals(user.get().getEmail(), mail);
    }
}
