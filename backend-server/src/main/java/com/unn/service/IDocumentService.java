package com.unn.service;

import com.unn.model.Document;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface IDocumentService {
    Optional<Document> createDocument(Document document, MultipartFile files);

    Optional<Document> findDocument(Long documentId);
    Optional<Document> findDocumentByResourceId(Long resourceId);

    Optional<Document> updateDocument(Document document, MultipartFile files);

    Optional<Document> deleteDocument(Long documentId);
}
