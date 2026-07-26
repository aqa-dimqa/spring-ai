package com.example.springai.middleware.service;

import jakarta.annotation.Nonnull;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentEdgeService {

    void addNewDocument(@Nonnull final MultipartFile multipartFile);

    void loadDocumentsFromResources();
}
