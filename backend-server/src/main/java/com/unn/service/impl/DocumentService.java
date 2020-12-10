package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.Document;
import com.unn.model.Resource;
import com.unn.repository.DocumentRepo;
import com.unn.repository.ResourceRepo;
import com.unn.service.IDocumentService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService {
  private final DocumentRepo documentRepo;
  private final ResourceRepo resourceRepo;

  @Override
  public Optional<Document> createDocument(String number, String description, Long resourceId) {
    Optional<Resource> resource = resourceRepo.findById(resourceId);
    Document document = new Document(number, description, resource.get());
    documentRepo.save(document);
    return Optional.of(document);
  }

  @Override
  public Optional<Document> findDocument(Long documentId) {
    return documentRepo.findById(documentId);
  }

  @Override
  public Optional<Document> findDocumentByResourceId(Long resourceId) {
    return documentRepo.findDocumentByResourceId(resourceId);
  }

  @Override
  public boolean updateDocument(Long documentId, String number, String description, Resource resource) {
    Optional<Document> document = documentRepo.findById(documentId);
    if (document.isPresent()) {
      document.get().setNumber(number);
      document.get().setDescription(description);
      document.get().setResource(resource);
      documentRepo.save(document.get());
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean deleteDocument(Long documentId) {
    Optional<Document> document = documentRepo.findById(documentId);
      if (document.isPresent()) {
        documentRepo.delete(document.get());
        return true;
      } else {
        return false;
      }
  }
}
