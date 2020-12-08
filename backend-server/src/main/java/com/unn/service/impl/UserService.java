package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.User;
import com.unn.repository.UserRepo;
import com.unn.service.IUserService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
  private final UserRepo userRepo;

  @Override
  public Optional<User> createUser(
    Long userTypeId,
    String username,
    String password,
    String mail
  ) {
    User user = new User(userTypeId, username, password, mail);
    userRepo.save(user);
    return Optional.of(user);
  }

  @Override
  public void addUser(User user) {
    userRepo.save(user);
  }

  @Override
  public Optional<User> findUser(String mail) {
    return userRepo.findByMail(mail);
  }

  @Override
  public Optional<User> findUser(Long id) {
    return userRepo.findById(id);
  }

  @Override
  public boolean deleteUser(String mail) {
    Optional<User> user = userRepo.findByMail(mail);
    if (user.isPresent()) {
      userRepo.delete(user.get());
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean deleteUser(Long id) {
    Optional<User> user = userRepo.findById(id);
    if (user.isPresent()) {
      userRepo.delete(user.get());
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean updateUser(String username, String password, String mail) {
    Optional<User> user = userRepo.findByMail(mail);
    if (user.isPresent()) {
      user.get().setUsername(username);
      user.get().setPassword(password);
      userRepo.save(user.get());
      return true;
    } else {
      return false;
    }
  }
}
