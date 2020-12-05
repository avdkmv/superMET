package com.unn.repository;

import com.unn.model.UserType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepo extends JpaRepository<UserType, Long> {}
