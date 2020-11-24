package com.unn.repository;

import com.unn.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {}
