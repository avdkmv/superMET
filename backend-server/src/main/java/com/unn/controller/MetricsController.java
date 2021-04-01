package com.unn.controller;

import java.io.File;

import com.unn.service.impl.MetricsService;
import com.unn.service.impl.ResponseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/metric")
@RequiredArgsConstructor
public class MetricsController {
    private final MetricsService metricsService;
    private final ResponseService responseService;

    @GetMapping("/get")
    public ResponseEntity<File> getStatistic() {
        return responseService.handleGetResponse(metricsService.createStatistic());
    }
}
