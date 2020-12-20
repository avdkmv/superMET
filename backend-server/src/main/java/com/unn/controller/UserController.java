package com.unn.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.unn.model.User;
import com.unn.service.impl.UserService;
import com.unn.service.impl.ValidationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final ValidationService validationService;

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
    Optional<User> user = userService.findUser(id);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @GetMapping("/mail/{mail}")
  public ResponseEntity<User> getUser(@PathVariable("mail") String mail) {
    Optional<User> user = userService.findUser(mail);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/registration")
  public ResponseEntity<User> registration(@RequestBody @Valid User user) {
    if (validationService.validateUserCreation(user)) {
      userService.createUser(user);
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/edit")
  public ResponseEntity<User> edit(@RequestBody @Valid User user) {
    if (validationService.validateUserUpdate(user)) {
      userService.updateUser(user);
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
    Optional<User> deletedUser = userService.deleteUser(id);
    if (deletedUser.isPresent()) {
      return ResponseEntity.ok(deletedUser.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/mail/{mail}/delete")
  public ResponseEntity<User> deleteUser(@PathVariable("mail") String mail) {
    Optional<User> deletedUser = userService.deleteUser(mail);
    if (deletedUser.isPresent()) {
      return ResponseEntity.ok(deletedUser.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
