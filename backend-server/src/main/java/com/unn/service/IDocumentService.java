package com.unn.service;

import java.util.Optional;

import com.unn.model.Document;
import com.unn.model.Resource;

public interface IDocumentService {
  Optional<Document> createDocument(
    String number,
    String description,
    Long resourceId
  );

  Optional<Document> findDocument(Long documentId);
  Optional<Document> findDocumentByResourceId(Long resourceId);

  boolean updateDocument(
    Long documentId,
    String number,
    String description,
    Resource resource
  );

  boolean deleteDocument(Long documentId);
}
