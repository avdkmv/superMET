package com.unn.controller;

import com.unn.service.impl.ScheduleService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class SchduleController {
  private final ScheduleService scheduleService;
}
