package com.unn.controller;

import com.unn.service.impl.MetricsService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/metric")
@RequiredArgsConstructor
public class MetricsController {
  private final MetricsService metricsService;
}
