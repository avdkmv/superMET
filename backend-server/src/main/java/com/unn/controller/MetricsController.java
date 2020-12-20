package com.unn.controller;

import com.unn.service.impl.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metric")
@RequiredArgsConstructor
public class MetricsController {
    private final MetricsService metricsService;
}
