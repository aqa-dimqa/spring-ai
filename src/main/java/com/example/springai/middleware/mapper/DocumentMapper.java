package com.example.springai.middleware.mapper;

import com.example.springai.domain.model.DocumentEntity;
import com.example.springai.middleware.model.response.DocumentShortResponse;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
import java.util.List;
import java.util.Objects;

@Component
public class DocumentMapper extends BaseMapper {

    @Value("${app.ai.document.chunk-size}")
    private int chunkSize;

    @SneakyThrows
    @Nonnull
    public DocumentEntity toEntity(@Nonnull final Resource resource,
                                   @Nonnull final String contentHash
    ) {
        return DocumentEntity.builder()
                .fileName(resource.getFilename())
                .contentHash(contentHash)
                .chunkNumber(chunkSize)
                .documentType(getDocumentType(Objects.requireNonNull(resource.getFilename())))
                .size(resource.contentLength())
                .build();

    }

    @Nonnull
    public DocumentEntity toEntity(@Nonnull final MultipartFile multipartFile,
                                   @Nonnull final String contentHash
    ) {
        return DocumentEntity.builder()
                .fileName(multipartFile.getName())
                .contentHash(contentHash)
                .chunkNumber(chunkSize)
                .documentType(getDocumentType(Objects.requireNonNull(multipartFile.getName())))
                .size(multipartFile.getSize())
                .build();
    }

    @Nonnull
    private String getDocumentType(@Nonnull final String filename) {
        return URLConnection.getFileNameMap().getContentTypeFor(filename);
    }

    public DocumentShortResponse toShortResponse(DocumentEntity document) {
        return DocumentShortResponse.builder()
                .id(document.getId())
                .title(document.getFileName())
                .createdAt(document.getCreatedAt())
                .build();
    }

    public List<DocumentShortResponse> toShortResponse(List<DocumentEntity> documents) {
        return documents.stream()
                .map(this::toShortResponse)
                .toList();
    }
}
