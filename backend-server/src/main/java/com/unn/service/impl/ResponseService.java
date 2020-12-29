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
        return handleResponse(body, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @Override
    public <T> ResponseEntity<T> handlePostResponse(Optional<T> body) {
        return handleResponse(body, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }

    @Override
    public <T> ResponseEntity<T> handleDeleteResponse(Optional<T> body) {
        return handleGetResponse(body);
    }

    public ResponseEntity<String> handleLoginResponse(Optional<String> body) {
        return handleResponse(body, HttpStatus.OK, HttpStatus.UNAUTHORIZED);
    }

    private <T> ResponseEntity<T> handleResponse(Optional<T> body, HttpStatus ok, HttpStatus err) {
        if (body.isPresent()) {
            return ResponseEntity.status(ok).body(body.get());
        } else {
            return ResponseEntity.status(err).build();
        }
    }
}
