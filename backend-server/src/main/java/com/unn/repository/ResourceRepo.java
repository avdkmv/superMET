package com.unn.repository;

import com.unn.model.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepo extends JpaRepository<Resource, Long> {}
