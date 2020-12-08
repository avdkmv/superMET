package com.unn.repository;

import java.util.Optional;

import com.unn.model.Document;
import com.unn.model.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
    Optional<Document> findDocumentByResourceId(Resource resource);
}
