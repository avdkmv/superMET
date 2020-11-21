package com.unn.service.impl;

import com.unn.model.Document;
import com.unn.service.IDocumentService;
import java.util.Optional;

public class DocumentService implements IDocumentService {

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
