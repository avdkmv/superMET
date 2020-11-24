package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.Document;
import com.unn.repository.DocumentRepo;
import com.unn.service.IDocumentService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService {
  private final DocumentRepo documentRepo;

  @Override
  public Optional<Document> createDocument(
    Long documentId,
    String number,
    String description
  ) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Document> findDocument(Long documentId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Document> findDocumentByResourceId(Long resourceId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Document> updateDocument(
    Long documentId,
    String number,
    String description
  ) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Document> deleteDocument(Long documentId) {
    // TODO:  implement method
    return null;
  }
}
