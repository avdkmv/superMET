package com.unn.service.impl;

import com.unn.service.IResponseService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseService implements IResponseService {

    @Override
    public <T> ResponseEntity<T> handleGetResponse(Optional<T> body) {
        if (body.isPresent()) {
            return ResponseEntity.ok(body.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public <T> ResponseEntity<T> handlePostResponse(Optional<T> body) {
        if (body.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(body.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public <T> ResponseEntity<T> handleDeleteResponse(Optional<T> body) {
        return handleGetResponse(body);
    }
}
