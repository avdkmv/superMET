package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.User;
import com.unn.repository.UserRepo;
import com.unn.service.IUserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
  private final UserRepo userRepo;

  @Override
  public Optional<User> createUser(User user) {
    userRepo.save(user);
    return Optional.of(user);
  }

  @Override
  public ResponseEntity<User> findUser(String mail) {
    Optional<User> user = userRepo.findByMail(mail);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Override
  public ResponseEntity<User> findUser(Long id) {
    Optional<User> user = userRepo.findById(id);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Override
  public HttpStatus deleteUser(String mail) {
    Optional<User> user = userRepo.findByMail(mail);
    if (user.isPresent()) {
      userRepo.delete(user.get());
      return HttpStatus.OK;
    } else {
      return HttpStatus.NOT_FOUND;
    }
  }

  @Override
  public HttpStatus deleteUser(Long id) {
    Optional<User> user = userRepo.findById(id);
    if (user.isPresent()) {
      userRepo.delete(user.get());
      return HttpStatus.OK;
    } else {
      return HttpStatus.NOT_FOUND;
    }
  }

  @Override
  public void updateUser(User user) {
    Optional<User> updatedUser = userRepo.findByMail(user.getMail());
    updatedUser.get().setUsername(user.getUsername());
    updatedUser.get().setPassword(user.getPassword());
    userRepo.save(updatedUser.get());
  }
}
