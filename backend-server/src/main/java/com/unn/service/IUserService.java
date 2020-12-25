package com.unn.service;

import java.util.List;
import java.util.Optional;
import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.User;

public interface IUserService {
    Optional<User> createUser(SignupRequest request);

    Optional<User> findUser(String mail);
    Optional<User> findUser(Long id);

    Optional<User> deleteUser(String mail);
    Optional<User> deleteUser(Long id);

    Optional<User> updateUser(User user);

    void clearTable();

    Optional<List<User>> getAllByType(UserTypes type);
}
