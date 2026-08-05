package com.example.springai.middleware.service.impl;

import com.example.springai.domain.enums.ResourceType;
import com.example.springai.domain.model.DocumentEntity;
import com.example.springai.domain.service.DocumentDomainService;
import com.example.springai.exception.ResourceAlreadyExistsException;
import com.example.springai.middleware.mapper.DocumentMapper;
import com.example.springai.middleware.model.response.DocumentShortResponse;
import com.example.springai.middleware.model.response.UuidIdResponse;
import com.example.springai.middleware.service.DocumentEdgeService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DocumentFacade implements DocumentEdgeService {

    private static final String PATH_TO_KNOWLEDGEBASE = "classpath:/knowledlgebase/**/*.txt";

    private final DocumentDomainService documentDomainService;
    private final ResourcePatternResolver resolver;
    private final VectorStore vectorStore;
    private final DocumentMapper documentMapper;

    @Value("${app.ai.document.chunk-size}")
    private int chunkSize;


    @Override
    public UuidIdResponse addNewDocument(@Nonnull MultipartFile multipartFile) {
        DocumentEntity document = processResource(multipartFile.getResource());
        return documentMapper.toUuidIdMapper(document);
    }

    @Override
    @SneakyThrows
    public void loadDocumentsFromResources() {
        Arrays.stream(resolver.getResources(PATH_TO_KNOWLEDGEBASE))
                .forEach(this::processResource);
    }

    @Nonnull
    @Override
    public List<DocumentShortResponse> getAllDocuments() {
        List<DocumentEntity> documents = documentDomainService.getAllDocuments();
        return documentMapper.toShortResponse(documents);
    }

    private DocumentEntity processResource(Resource resource) throws ResourceAlreadyExistsException {

        String contentHash = calculateContentHash(resource);
        boolean fileExists = documentDomainService.existsByFileNameAndContentHash(
                Objects.requireNonNull(resource.getFilename()),
                contentHash);

        if (fileExists)
            throw new ResourceAlreadyExistsException(
                    ResourceType.DOCUMENT,
                    Map.of(
                            "file name", resource.getFilename(),
                            "content hash", contentHash
                    )
            );

        List<Document> documents = new TextReader(resource).get();
        TokenTextSplitter textSplitter = TokenTextSplitter.builder()
                .withChunkSize(chunkSize)
                .build();
        List<Document> chunks = textSplitter.apply(documents);
        vectorStore.accept(chunks);

        DocumentEntity documentEntity = documentMapper.toEntity(resource, contentHash);
        return documentDomainService.save(documentEntity);

    }

    @SneakyThrows
    private String calculateContentHash(Resource resource) {
        return DigestUtils.md5DigestAsHex(resource.getInputStream());
    }

}
