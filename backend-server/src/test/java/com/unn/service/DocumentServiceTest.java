package com.unn.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.print.Doc;

import com.jayway.jsonpath.Option;
import com.unn.model.Document;
import com.unn.model.Resource;
import com.unn.service.impl.DocumentService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

public class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;

    @Test 
    void createDocument() throws IOException {
        documentService.clearTable();
        
        String number = "0";
        String description = "description";
        Long resourceId = 0L;

        File myFile = new File("123.txt");
        myFile.createNewFile();
        FileInputStream file = new FileInputStream(myFile);
        MultipartFile multipartFile = new MockMultipartFile("123.txt", file);

        Document document = new Document(number, description); 
        Optional<Document> createdDocument = documentService.createDocument(document, multipartFile);

        
        Assert.assertEquals(createdDocument.get().getNumber(), number);
        Assert.assertEquals(createdDocument.get().getDescription(), description);
        Assert.assertEquals(createdDocument.get().getResource(), resourceId);
        
        documentService.clearTable();
    }

    @Test
    void findDocumentById() throws IOException{
        documentService.clearTable();
        
        String number = "0";
        String description = "description";
        Long resourceId = 0L;

        File myFile = new File("123.txt");
        myFile.createNewFile();
        FileInputStream file = new FileInputStream(myFile);
        MultipartFile multipartFile = new MockMultipartFile("123.txt", file);

        Document document = new Document(number, description); 
        Optional<Document> createdDocument = documentService.createDocument(document, multipartFile);
        
        Optional<Document> gotDocument = documentService.findDocument(createdDocument.get().getId());
        Assert.assertEquals(gotDocument.get().getNumber(), number);
        Assert.assertEquals(gotDocument.get().getDescription(), description);
        Assert.assertEquals(gotDocument .get().getResource(), resourceId);
        
        documentService.clearTable();
    }

    @Test
    void findDocumentByResourceId() throws IOException {
        documentService.clearTable();
        
        String number = "0";
        String description = "description";
        Long resourceId = 0L;

        File myFile = new File("123.txt");
        myFile.createNewFile();
        FileInputStream file = new FileInputStream(myFile);
        MultipartFile multipartFile = new MockMultipartFile("123.txt", file);

        Document document = new Document(number, description); 
        Optional<Document> createdDocument = documentService.createDocument(document, multipartFile);
      
        
        Optional<Document> gotDocument = documentService.findDocumentByResourceId(
                                            createdDocument.get().getResource().getId());

        Assert.assertEquals(gotDocument.get().getNumber(), number);
        Assert.assertEquals(gotDocument.get().getDescription(), description);
        Assert.assertEquals(gotDocument .get().getResource(), resourceId);
        
        documentService.clearTable();
    }

    @Test
    void updateDocument() throws IOException {
        documentService.clearTable();

        String number1 = "1";
        String description1 = "description1";
        Resource resource1 = new Resource();
        Long resourceId1 = 1L;
        resource1.setId(resourceId1);

        File myFile = new File("123.txt");
        myFile.createNewFile();
        FileInputStream file = new FileInputStream(myFile);
        MultipartFile multipartFile = new MockMultipartFile("123.txt", file);

        Document document = new Document(number1, description1); 
        Optional<Document> createdDocument = documentService.createDocument(document, multipartFile);
              
        String number2 = "2";
        String description2 = "description2";
        Resource resource2 = new Resource();
        Long resourceId2 = 2L;
        resource2.setId(resourceId2);

        Document document2 = new Document(number2, description2);
        documentService.updateDocument(document2, multipartFile);

        Optional<Document> gotDocument = documentService.findDocumentByResourceId(resourceId2);

        Assert.assertEquals(gotDocument.get().getNumber(), number2);
        Assert.assertEquals(gotDocument.get().getDescription(), description2);
        Assert.assertEquals(gotDocument .get().getResource(), resourceId2);

        documentService.clearTable();
    }

    @Test
    void deleteDocument() throws IOException {
        documentService.clearTable();

        String number1 = "1";
        String description1 = "description1";
        Resource resource1 = new Resource();
        Long resourceId1 = 1L;
        resource1.setId(resourceId1);

        File myFile = new File("123.txt");
        myFile.createNewFile();
        FileInputStream file = new FileInputStream(myFile);
        MultipartFile multipartFile = new MockMultipartFile("123.txt", file);

        Document document = new Document(number1, description1); 
        Optional<Document> createdDocument = documentService.createDocument(document, multipartFile);
        
        Assert.assertTrue(documentService.deleteDocument(createdDocument.get().getId()).isPresent());
        
        Optional<Document> gotDocument = documentService.findDocument(createdDocument.get().getId());

        Assert.assertFalse(gotDocument.isPresent());
    }
}
