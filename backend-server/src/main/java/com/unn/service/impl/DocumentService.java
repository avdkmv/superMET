package com.unn.service.impl;

import com.unn.constants.Constant;
import com.unn.model.Document;
import com.unn.model.Resource;
import com.unn.repository.DocumentRepo;
import com.unn.repository.ResourceRepo;
import com.unn.service.IDocumentService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentService implements IDocumentService {
    private final DocumentRepo documentRepo;
    private final ResourceRepo resourceRepo;

    @Override
    public Optional<Document> createDocument(Document document, MultipartFile file) {
        Optional<Resource> resource = Optional.empty();
        try {
            if (file != null) {
                resource = addResource(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.setResource(resource.get());
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
        return documentRepo.findByResourceId(resourceId);
    }

    @Override
    public Optional<Document> updateDocument(Document document, MultipartFile file) {
        Optional<Document> updatedDocument = documentRepo.findById(document.getId());
        updatedDocument.get().setDescription(document.getDescription());
        try {
            if (file != null) {
                Optional<Resource> resource = updateResource(file, updatedDocument.get());
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
            String filePath = document.get().getResource().getName();
            Long resourceId = document.get().getResource().getId();
            File oldFile = new File(Constant.UPLOAD_PATH + "/" + filePath);
            oldFile.delete();
            documentRepo.delete(document.get());
            resourceRepo.deleteById(resourceId);
        }
        return document;
    }

    private Optional<Resource> addResource(MultipartFile file) throws IOException {
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        File dir = Constant.UPLOAD_PATH.toFile();
        if (!dir.exists()) {
            dir.mkdir();
        }
        String as = dir.getAbsolutePath();
        file.transferTo(new File(Constant.UPLOAD_PATH + "/" + resultFilename));

        Resource resource = new Resource(resultFilename);
        return Optional.of(resourceRepo.save(resource));
    }

    private Optional<Resource> updateResource(MultipartFile file, Document document) throws IOException {
        File oldFile = new File(Constant.UPLOAD_PATH + "/" + document.getResource().getName());
        oldFile.delete();

        Optional<Document> dbDoc = documentRepo.findById(document.getId());
        dbDoc.get().setResource(null);
        documentRepo.save(dbDoc.get());

        resourceRepo.deleteById(document.getResource().getId());

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(Constant.UPLOAD_PATH + "/" + resultFilename));

        Resource resource = new Resource(resultFilename, document);
        return Optional.of(resourceRepo.save(resource));
    }

    @Override
    public Optional<List<Document>> findAll() {
        return Optional.of(documentRepo.findAll());
    }

}
