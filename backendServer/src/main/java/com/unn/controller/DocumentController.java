package com.unn.controller;

import com.unn.service.impl.DocumentService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
  private final DocumentService documentService;
}
