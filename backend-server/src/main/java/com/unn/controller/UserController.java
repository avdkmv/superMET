package com.unn.controller;

import javax.validation.Valid;

import com.unn.model.User;
import com.unn.service.impl.UserService;
import com.unn.service.impl.ValidationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final ValidationService validationService;

  @GetMapping("/getById")
  public ResponseEntity<User> getUser(@RequestParam Long id) {
    return userService.findUser(id);
  }

  @GetMapping("/getByMail")
  public ResponseEntity<User> getUser(@RequestParam String mail) {
    return userService.findUser(mail);
  }

  @PostMapping("/registration")
  public ResponseEntity<User> registration(@RequestBody @Valid User user) {
    if (validationService.validateUserCreation(user)) {
      userService.createUser(user);
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PostMapping("/edit")
  public ResponseEntity<User> edit(@RequestBody @Valid User user) {
    if (validationService.validateUserUpdate(user)) {
      userService.updateUser(user);
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PostMapping("/deleteById")
  public HttpStatus deleteUser(@RequestParam Long id) {
    return userService.deleteUser(id);
  }

  @PostMapping("/deleteByMail")
  public HttpStatus deleteUser(@RequestParam String mail) {
    return userService.deleteUser(mail);
  }
}
