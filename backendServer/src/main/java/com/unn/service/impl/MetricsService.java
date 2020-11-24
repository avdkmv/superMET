package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.Document;
import com.unn.service.IMetricsService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetricsService implements IMetricsService {

  @Override
  public Optional<Document> findMetrics() {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Document> collectMetrix() {
    // TODO:  implement method
    return null;
  }
}
