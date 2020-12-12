package com.unn.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import com.unn.constants.Constant;
import com.unn.model.Document;
import com.unn.model.Resource;
import com.unn.repository.DocumentRepo;
import com.unn.repository.ResourceRepo;
import com.unn.service.IDocumentService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService {
  private final DocumentRepo documentRepo;
  private final ResourceRepo resourceRepo;

  @Override
  public Optional<Document> createDocument(
    Document document,
    MultipartFile file
  ) {
    try {
      if (file != null) {
        String filename = addResource(file, document);
        Optional<Resource> resource = resourceRepo.findByName(filename);
        document.setResource(resource.get());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      documentRepo.save(document);
    }
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
  public Optional<Document> updateDocument(
    Document document,
    MultipartFile file
  ) {
    Optional<Document> updatedDocument = documentRepo.findById(
      document.getId()
    );
    updatedDocument.get().setDescription(document.getDescription());
    try {
      if (file != null) {
        String filename = updateResource(file, updatedDocument.get());
        Optional<Resource> resource = resourceRepo.findByName(filename);
        updatedDocument.get().setResource(resource.get());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      documentRepo.save(updatedDocument.get());
    }
    return updatedDocument;
  }

  @Override
  public Optional<Document> deleteDocument(Long documentId) {
    Optional<Document> document = documentRepo.findById(documentId);
    if (document.isPresent()) {
      documentRepo.delete(document.get());
    }
    return document;
  }

  private String addResource(MultipartFile file, Document document)
    throws IOException {
    String uuidFile = UUID.randomUUID().toString();
    String resultFilename = uuidFile + "." + file.getOriginalFilename();

    file.transferTo(new File(Constant.UPLOAD_PATH + "/" + resultFilename));

    Resource resource = new Resource(file.getOriginalFilename(), document);
    resourceRepo.save(resource);
    return resource.getName();
  }

  private String updateResource(MultipartFile file, Document document)
    throws IOException {
    resourceRepo.deleteById(document.getResource().getId());

    String uuidFile = UUID.randomUUID().toString();
    String resultFilename = uuidFile + "." + file.getOriginalFilename();

    file.transferTo(new File(Constant.UPLOAD_PATH + "/" + resultFilename));

    Resource resource = new Resource(file.getOriginalFilename(), document);
    resourceRepo.save(resource);
    return resource.getName();
  }
}
