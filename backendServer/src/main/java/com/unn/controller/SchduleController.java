package com.unn.controller;

import com.unn.service.impl.ScheduleService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class SchduleController {
  private final ScheduleService scheduleService;
}
