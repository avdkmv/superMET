package com.unn.repository;

import java.util.List;
import java.util.Optional;
import com.unn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    <T> Optional<T> findById(Long id, Class<T> type);
    Optional<User> findByEmail(String mail);
    Optional<List<User>> findAllByTypeId(Long typeId);
    Optional<User> findByUsername(String name);
    <T> Optional<T> findByUsername(String name, Class<T> type);

    boolean existsByUsername(String name);
}
