package com.unn.controller;

import com.unn.service.impl.AppointmentService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
  private final AppointmentService appointmentService;
}
