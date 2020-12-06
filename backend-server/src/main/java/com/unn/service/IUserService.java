package com.unn.service;

import java.util.Optional;

import com.unn.model.User;

public interface IUserService {
  Optional<User> createUser(
    Long userTypeId,
    String username,
    String password,
    String mail
  );

  Optional<User> findUser(String mail);
  Optional<User> findUser(Long id);

  boolean deleteUser(String mail);
  boolean deleteUser(Long id);

  boolean updateUser(String username, String password, String mail);
}
