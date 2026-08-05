package com.example.springai.domain.service;

import com.example.springai.domain.model.DocumentEntity;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.UUID;

public interface DocumentDomainService {

    @Nonnull
    DocumentEntity save(@Nonnull final DocumentEntity source);

    boolean existsByFileNameAndContentHash(@Nonnull final String fileName, @Nonnull final String contentHash);

    @Nonnull
    List<DocumentEntity> getAllDocuments();

    @Nonnull
    List<DocumentEntity> getAllDocumentsByIds(@Nonnull final List<UUID> documentIds);

    @Nonnull
    DocumentEntity getDocument(@Nonnull final UUID userId, @Nonnull final UUID id);

    @Nonnull
    DocumentEntity getDocument(@Nonnull final UUID id);

}