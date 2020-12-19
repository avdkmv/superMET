package com.unn.service;

import java.util.Optional;

import com.jayway.jsonpath.Option;
import com.unn.model.Document;
import com.unn.model.Resource;
import com.unn.service.impl.DocumentService;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;

    @Test 
    void createDocument() {
        documentService.clearTable();
        
        String number = "0";
        String description = "description";
        Long resourceId = 0L;
        Optional<Document> createdDocument = documentService.createDocument(number, description, resourceId)
        
        Assert.assertEquals(createdDocument.get().getNumber(), number);
        Assert.assertEquals(createdDocument.get().getDescription(), description);
        Assert.assertEquals(createdDocument.get().getResource(), resourceId);
        
        documentService.clearTable();
    }

    @Test
    void findDocumentById(){
        documentService.clearTable();
        
        String number = "0";
        String description = "description";
        Long resourceId = 0L;
        Optional<Document> createdDocument = documentService.createDocument(number, description, resourceId);
        
        Optional<Document> gotDocument = documentService.findDocument(createdDocument.get().getId());
        Assert.assertEquals(gotDocument.get().getNumber(), number);
        Assert.assertEquals(gotDocument.get().getDescription(), description);
        Assert.assertEquals(gotDocument .get().getResource(), resourceId);
        
        documentService.clearTable();
    }

    @Test
    void findDocumentByResourceId() {
        documentService.clearTable();
        
        String number = "0";
        String description = "description";
        Long resourceId = 0L;
        Optional<Document> createdDocument = documentService.createDocument(number, description, resourceId);
        
        Optional<Document> gotDocument = documentService.findDocumentByResourceId(
                                            createdDocument.get().getResource().getId());

        Assert.assertEquals(gotDocument.get().getNumber(), number);
        Assert.assertEquals(gotDocument.get().getDescription(), description);
        Assert.assertEquals(gotDocument .get().getResource(), resourceId);
        
        documentService.clearTable();
    }
/*
    @Test
    void updateDocument() {
        /*
        Long documentId
        String number
        String description
        Resource resource
        documentService.clearTable();

        String number = "0";
        String description = "description";
        Resource resource = new Resource();
        Long resourceId = 1L;
        resource.setId(resourceId);

        Optional<Document> createdDocument = documentService.createDocument(number, description, resourceId);
        
        Optional<Document> gotDocument = documentService.findDocumentByResourceId(
                                            createdDocument.get().getResource().getId());

        Assert.assertEquals(gotDocument.get().getNumber(), number);
        Assert.assertEquals(gotDocument.get().getDescription(), description);
        Assert.assertEquals(gotDocument .get().getResource(), resourceId);

        documentService.clearTable();
    }
*/
}
