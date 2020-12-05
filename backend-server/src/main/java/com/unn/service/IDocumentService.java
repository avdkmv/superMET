package com.unn.service;

import java.util.Optional;

import com.unn.model.Document;

public interface IDocumentService {
  Optional<Document> createDocument(
    Long documentId,
    String number,
    String description
  );

  Optional<Document> findDocument(Long documentId);
  Optional<Document> findDocumentByResourceId(Long resourceId);

  Optional<Document> updateDocument(
    Long documentId,
    String number,
    String description
  );

  Optional<Document> deleteDocument(Long documentId);
}
