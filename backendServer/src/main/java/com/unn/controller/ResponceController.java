package com.unn.controller;

import com.unn.service.impl.ResponceService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/responce")
@RequiredArgsConstructor
public class ResponceController {
  private final ResponceService responceService;
}
