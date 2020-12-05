package com.unn.service.impl;

import com.unn.service.IAuthService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

  @Override
  public boolean authenticate(Long userId) {
    // TODO:  implement method
    return false;
  }
}
