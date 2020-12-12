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
  public Optional<User> createUser(User user) {
    userRepo.save(user);
    return Optional.of(user);
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
  public Optional<User> deleteUser(String mail) {
    Optional<User> user = userRepo.findByMail(mail);
    if (user.isPresent()) {
      userRepo.deleteById(user.get().getId());
    }
    return user;
  }

  @Override
  public Optional<User> deleteUser(Long id) {
    Optional<User> user = userRepo.findById(id);
    if (user.isPresent()) {
      userRepo.deleteById(user.get().getId());
    }
    return user;
  }

  @Override
  public Optional<User> updateUser(User user) {
    Optional<User> updatedUser = userRepo.findByMail(user.getMail());
    updatedUser.get().setUsername(user.getUsername());
    updatedUser.get().setPassword(user.getPassword());
    userRepo.save(updatedUser.get());
    return updatedUser;
  }
}
