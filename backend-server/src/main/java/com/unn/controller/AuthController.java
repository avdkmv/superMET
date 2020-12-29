package com.unn.controller;

import com.unn.dto.LoginRequest;
import com.unn.dto.SignupRequest;
import com.unn.model.User;
import com.unn.security.SecurityService;
import com.unn.service.impl.ResponseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SecurityService security;
    private final ResponseService responseService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
        return responseService.handlePostResponse(security.signup(request));
    }

    @PostMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return responseService.handleLoginResponse(security.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(Authentication auth) {
        return responseService.handleGetResponse(security.logout(auth));
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> isLoggedIn(Authentication auth) {
        return responseService.handleGetResponse(security.isLoggedIn(auth));
    }
}
