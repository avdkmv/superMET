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

  Optional<User> deleteUser(String mail);
  Optional<User> deleteUser(Long id);

  Optional<User> updateUser(String username, String password, String mail);
}
