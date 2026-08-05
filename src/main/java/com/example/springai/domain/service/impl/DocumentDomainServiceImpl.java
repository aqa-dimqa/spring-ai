package com.example.springai.domain.service.impl;

import com.example.springai.domain.enums.ResourceType;
import com.example.springai.domain.model.DocumentEntity;
import com.example.springai.domain.repository.DocumentRepository;
import com.example.springai.domain.service.DocumentDomainService;
import com.example.springai.exception.ResourceNotFoundException;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentDomainServiceImpl implements DocumentDomainService {

    private final DocumentRepository documentRepository;

    @Nonnull
    @Override
    public DocumentEntity save(@Nonnull final DocumentEntity source) {
        return documentRepository.save(source);
    }

    @Override
    public boolean existsByFileNameAndContentHash(@Nonnull final String fileName, @Nonnull final String contentHash) {
        return documentRepository.existsByFileNameAndContentHash(fileName, contentHash);
    }

    @Nonnull
    @Override
    public List<DocumentEntity> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Nonnull
    @Override
    public List<DocumentEntity> getAllDocumentsByIds(@Nonnull final List<UUID> documentsIds) {
        return documentRepository.findAllByIdIn(documentsIds);
    }

    @Nonnull
    @Override
    public DocumentEntity getDocument(@Nonnull final UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.DOCUMENT, id.toString()));
    }

    @Nonnull
    @Override
    public DocumentEntity getDocument(@Nonnull final UUID userId, @Nonnull final UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                ResourceType.DOCUMENT,
                                "user_id = [%s] and id = [%s]".formatted(userId.toString(), id.toString())
                        )
                );
    }
}
