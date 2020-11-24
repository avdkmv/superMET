package com.unn.repository;

import com.unn.model.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApppointmentRepo extends JpaRepository<Appointment, Long> {}
