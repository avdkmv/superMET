package com.unn.service;

import java.util.Optional;

import com.unn.model.Document;

public interface IMetricsService {
  Optional<Document> findMetrics(); // TODO: params?

  Optional<Document> collectMetrix();
}
