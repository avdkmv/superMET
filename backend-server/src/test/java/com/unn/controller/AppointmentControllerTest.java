package com.unn.controller;

import com.unn.service.impl.AppointmentService;
import com.unn.service.impl.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentControllerTest extends AbstractControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    private final String username = "username";
    private final String password = "password";
    private final String mail = "mail";

}
