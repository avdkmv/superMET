package com.unn.controller;

import java.io.File;
import java.util.Optional;

import com.unn.service.impl.MetricsService;

import org.springframework.http.HttpStatus;
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

    @GetMapping("/get")
    public ResponseEntity<File> getStatistic() {
        Optional<File> file = metricsService.createStatistic();
        if (file.isPresent()) {
            return ResponseEntity.ok(file.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
