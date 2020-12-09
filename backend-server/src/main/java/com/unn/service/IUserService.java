package com.unn.service;

import java.util.Optional;

import com.unn.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IUserService {
  Optional<User> createUser(User user);

  ResponseEntity<User> findUser(String mail);
  ResponseEntity<User> findUser(Long id);

  HttpStatus deleteUser(String mail);
  HttpStatus deleteUser(Long id);

  void updateUser(User user);
}
