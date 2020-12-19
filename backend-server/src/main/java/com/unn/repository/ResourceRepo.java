package com.unn.repository;

import java.util.Optional;

import com.unn.model.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepo extends JpaRepository<Resource, Long> {
    Optional<Resource> findByName(String name);
}
