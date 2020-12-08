package com.unn.controller;

import javax.validation.Valid;

import com.unn.model.User;
import com.unn.service.impl.UserService;
import com.unn.service.impl.ValidationService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final ValidationService validationService;

  @PostMapping("/registration")
  public HttpStatus registration(@RequestBody @Valid User user) {
    if (validationService.validateUser(user)) {
      userService.addUser(user);
      return HttpStatus.OK;
    } else {
      return HttpStatus.CONFLICT;
    }
  }
}
