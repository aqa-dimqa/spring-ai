package com.example.springai.middleware.service;

import com.example.springai.middleware.model.response.DocumentShortResponse;
import com.example.springai.middleware.model.response.UuidIdResponse;
import jakarta.annotation.Nonnull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentEdgeService {

    UuidIdResponse addNewDocument(@Nonnull final MultipartFile multipartFile);

    List<DocumentShortResponse> getAllDocuments();

    void loadDocumentsFromResources();
}