package com.unn.controller;

import com.unn.service.impl.ResponceService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/responce")
@RequiredArgsConstructor
public class ResponceController {
  private final ResponceService responceService;
}
