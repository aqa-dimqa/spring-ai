package com.example.springai.domain.repository;

import com.example.springai.domain.model.DocumentEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {

    boolean existsByFileNameAndContentHash(@Nonnull final String fileName, @Nonnull final String contentHash);

    @Nonnull
    List<DocumentEntity> findAllByIdIn(@Nonnull final List<UUID> ids);

}
