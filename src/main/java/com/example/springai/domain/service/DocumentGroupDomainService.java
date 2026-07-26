package com.example.springai.domain.service;

import com.example.springai.domain.model.DocumentEntity;
import jakarta.annotation.Nonnull;

import java.util.UUID;

public interface DocumentGroupDomainService {

    @Nonnull
    DocumentEntity save(@Nonnull final DocumentEntity source);

    @Nonnull
    DocumentEntity getById(@Nonnull final UUID id);



}
