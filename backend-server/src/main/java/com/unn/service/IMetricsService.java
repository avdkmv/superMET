package com.unn.service;

import com.unn.model.Document;
import java.util.Optional;

public interface IMetricsService {
    Optional<Document> findMetrics(); // TODO: params?

    Optional<Document> collectMetrix();
}
