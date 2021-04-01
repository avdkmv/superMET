package com.unn.service;

import java.io.File;
import java.util.Optional;

public interface IMetricsService {
    Optional<File> createStatistic();
}
