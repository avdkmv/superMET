package com.unn.service;

import java.util.Optional;

import javax.print.Doc;

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

    @Test
    void updateDocument() {
        documentService.clearTable();

        String number1 = "1";
        String description1 = "description1";
        Resource resource1 = new Resource();
        Long resourceId1 = 1L;
        resource1.setId(resourceId1);

        Optional<Document> createdDocument = documentService.createDocument(number1, description1, resourceId1);
        
        String number2 = "2";
        String description2 = "description2";
        Resource resource2 = new Resource();
        Long resourceId2 = 2L;
        resource2.setId(resourceId2);

        documentService.updateDocument(resourceId2, number2, description2, resource2);

        Optional<Document> gotDocument = documentService.findDocumentByResourceId(resourceId2);

        Assert.assertEquals(gotDocument.get().getNumber(), number2);
        Assert.assertEquals(gotDocument.get().getDescription(), description2);
        Assert.assertEquals(gotDocument .get().getResource(), resourceId2);

        documentService.clearTable();
    }

    @Test
    void deleteDocument() {
        documentService.clearTable();

        String number1 = "1";
        String description1 = "description1";
        Resource resource1 = new Resource();
        Long resourceId1 = 1L;
        resource1.setId(resourceId1);

        Optional<Document> createdDocument = documentService.createDocument(number1, description1, resourceId1);
        
        Assert.assertTrue(documentService.deleteDocument(createdDocument.get().getId()));
        
        Optional<Document> gotDocument = documentService.findDocument(createdDocument.get().getId());

        Assert.assertFalse(gotDocument.isPresent());
    }
}
