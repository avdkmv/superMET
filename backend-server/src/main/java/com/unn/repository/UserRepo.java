package com.unn.repository;

import java.util.List;
import java.util.Optional;
import com.unn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByMail(String mail);
    Optional<List<User>> findAllByUserTypeId(Long userTypeId);
}
