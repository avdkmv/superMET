package com.unn.service;

import com.unn.model.Document;
import com.unn.repository.ResourceRepo;
import com.unn.service.impl.DocumentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;

    @Before
    @After
    public void clear() {
        Optional<List<Document>> retAllDocs = documentService.findAll();
        List<Document> allDocs = retAllDocs.get();
        for (int i = 0; i < allDocs.size(); i++) {
            documentService.deleteDocument(allDocs.get(i).getId());
        }
    }

    private String number = "num";
    private String desc = "desc";
    private String mockName = "file";
    private String fileName = "testFile.txt";
    private String content = "temptemptemp";

    @Test
    public void createDocumentTest() throws IOException {
        Document doc = new Document(number, desc);
        MockMultipartFile testFile = new MockMultipartFile(mockName, fileName, null, content.getBytes());
        Optional<Document> createdDoc = documentService.createDocument(doc, testFile);

        Optional<Document> retDoc = documentService.findDocument(createdDoc.get().getId());
        assertEquals(number, retDoc.get().getNumber());
        assertEquals(desc, retDoc.get().getDescription());
        assertTrue(retDoc.get().getResource().getName().contains(fileName));
    }

    @Test
    public void updateDocumentTest() throws IOException {
        Document doc = new Document(number, desc);
        MockMultipartFile testFile = new MockMultipartFile(mockName, fileName, null, content.getBytes());
        Optional<Document> createdDoc = documentService.createDocument(doc, testFile);

        String newDesc = "descNEW";
        createdDoc.get().setDescription(newDesc);
        String mockNameNew = "fileNEW";
        String fileNameNew = "testFileNEW.txt";
        String contentNew = "temptemptempNEW";
        MockMultipartFile testFileNew = new MockMultipartFile(mockNameNew, fileNameNew, null, contentNew.getBytes());
        Optional<Document> updDoc = documentService.updateDocument(createdDoc.get(), testFileNew);

        Optional<Document> retDoc = documentService.findDocument(updDoc.get().getId());
        assertEquals(number, retDoc.get().getNumber());
        assertEquals(newDesc, retDoc.get().getDescription());
        assertTrue(retDoc.get().getResource().getName().contains(fileNameNew));
    }

    @Test
    public void deleteDocumentTest() throws IOException {
        Document doc = new Document(number, desc);
        MockMultipartFile testFile = new MockMultipartFile(mockName, fileName, null, content.getBytes());
        Optional<Document> createdDoc = documentService.createDocument(doc, testFile);

        documentService.deleteDocument(createdDoc.get().getId());

        Optional<Document> retDoc = documentService.findDocument(createdDoc.get().getId());
        assertTrue(retDoc.isEmpty());
    }

    @Test
    public void findDocumentByResourseIdTest() throws IOException {
        Document doc = new Document(number, desc);
        MockMultipartFile testFile = new MockMultipartFile(mockName, fileName, null, content.getBytes());
        Optional<Document> createdDoc = documentService.createDocument(doc, testFile);

        Optional<Document> retDoc = documentService.findDocumentByResourceId(createdDoc.get().getResource().getId());
        assertEquals(number, retDoc.get().getNumber());
        assertEquals(desc, retDoc.get().getDescription());
        assertTrue(retDoc.get().getResource().getName().contains(fileName));
    }

}
