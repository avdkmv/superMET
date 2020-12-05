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
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<User> findUser(String mail) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<User> findUser(Long id) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<User> deleteUser(String mail) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<User> deleteUser(Long id) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<User> updateUser(
    String username,
    String password,
    String mail
  ) {
    // TODO Auto-generated method stub
    return null;
  }
}
