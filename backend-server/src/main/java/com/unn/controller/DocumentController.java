package com.unn.controller;

import java.util.Optional;

import com.unn.model.Document;
import com.unn.service.impl.DocumentService;
import com.unn.service.impl.ValidationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
  private final DocumentService documentService;
  private final ValidationService validationService;

  @GetMapping("/{id}")
  public ResponseEntity<Document> getUser(@PathVariable("id") Long id) {
    Optional<Document> document = documentService.findDocument(id);
    if (document.isPresent()) {
      return ResponseEntity.ok(document.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @GetMapping("/resource/{id}")
  public ResponseEntity<Document> getByResource(@PathVariable("id") Long id) {
    Optional<Document> document = documentService.findDocumentByResourceId(id);
    if (document.isPresent()) {
      return ResponseEntity.ok(document.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/add")
  public ResponseEntity<Document> addDocument(
    @RequestParam Document document,
    @RequestParam(name = "resource", required = false) MultipartFile resource
  ) {
    if (validationService.validateDocumentCreate(document)) {
      documentService.createDocument(document, resource);
      return ResponseEntity.ok(document);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/update")
  public ResponseEntity<Document> updateDocument(
    @RequestParam Document document,
    @RequestParam(name = "resource", required = false) MultipartFile resource
  ) {
    if (validationService.validateDocumentUpdate(document)) {
      documentService.updateDocument(document, resource);
      return ResponseEntity.ok(document);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Document> deleteDocument(@PathVariable("id") Long id) {
    Optional<Document> deletedDocument = documentService.deleteDocument(id);
    if (deletedDocument.isPresent()) {
      return ResponseEntity.ok(deletedDocument.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
