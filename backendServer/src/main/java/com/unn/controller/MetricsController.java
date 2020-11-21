package com.unn.controller;

import com.unn.service.impl.MetricsService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/metric")
@RequiredArgsConstructor
public class MetricsController {
  private final MetricsService metricsService;
}
