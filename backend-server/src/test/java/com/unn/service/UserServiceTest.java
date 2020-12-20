package com.unn.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.unn.model.User;
import com.unn.service.impl.UserService;
import java.util.Optional;
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

    private final Long userTypeId = 0L;
    private final String username = "username";
    private final String password = "password";
    private final String mail = "mail";
    private final User user = new User(userTypeId, username, password, mail);

    @Before
    @After
    public void clear() {
        userService.clearTable();
    }

    @Test
    public void createUserTest() {
        Optional<User> createdUser = userService.createUser(user);
        assertEquals(createdUser.get().getUsername(), username);
        assertEquals(createdUser.get().getPassword(), password);
        assertEquals(createdUser.get().getMail(), mail);
    }

    @Test
    public void findUserByIdTest() {
        Optional<User> createdUser = userService.createUser(user);
        Optional<User> retUser = userService.findUser(createdUser.get().getId());
        assertEquals(retUser.get().getUsername(), username);
        assertEquals(retUser.get().getPassword(), password);
        assertEquals(retUser.get().getMail(), mail);
    }

    @Test
    public void findUserByMailTest() {
        Optional<User> createdUser = userService.createUser(user);
        Optional<User> retUser = userService.findUser(createdUser.get().getMail());
        assertEquals(retUser.get().getUsername(), username);
        assertEquals(retUser.get().getPassword(), password);
        assertEquals(retUser.get().getMail(), mail);
    }

    @Test
    public void deleteUserByIdTest() {
        Optional<User> createdUser = userService.createUser(user);
        userService.deleteUser(createdUser.get().getId());
        Optional<User> retUser = userService.findUser(mail);
        assertTrue(retUser.isEmpty());
    }

    @Test
    public void deleteUserByMailTest() {
        Optional<User> createdUser = userService.createUser(user);
        userService.deleteUser(createdUser.get().getMail());
        Optional<User> retUser = userService.findUser(mail);
        assertTrue(retUser.isEmpty());
    }

    @Test
    public void updateUserByMailTest() {
        final String usernameNew = "username1";
        final String passwordNew = "password1";

        Optional<User> createdUser = userService.createUser(user);
        User modifyUser = createdUser.get();
        modifyUser.setUsername(usernameNew);
        modifyUser.setPassword(passwordNew);
        userService.updateUser(modifyUser);
        Optional<User> retUser = userService.findUser(mail);
        assertEquals(retUser.get().getUsername(), usernameNew);
        assertEquals(retUser.get().getPassword(), passwordNew);
        assertEquals(retUser.get().getMail(), mail);
    }
}
