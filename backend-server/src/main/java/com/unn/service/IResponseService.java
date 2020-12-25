package com.unn.service;

import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface IResponseService {
    public <T> ResponseEntity<T> handleGetResponse(Optional<T> body);
    public <T> ResponseEntity<T> handlePostResponse(Optional<T> body);
    public <T> ResponseEntity<T> handleDeleteResponse(Optional<T> body);
}
