package com.example.springai.middleware.service.impl;

import com.example.springai.domain.enums.ResourceType;
import com.example.springai.domain.model.DocumentEntity;
import com.example.springai.domain.service.DocumentDomainService;
import com.example.springai.exception.ResourceAlreadyExistsException;
import com.example.springai.middleware.mapper.DocumentMapper;
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
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DocumentFacade implements DocumentEdgeService {

    private final DocumentDomainService documentDomainService;
    private final ResourcePatternResolver resolver;
    private final VectorStore vectorStore;
    private final DocumentMapper documentMapper;

    @Value("${app.ai.document.chunk-size}")
    private int chunkSize;


    @Override
    public void addNewDocument(@Nonnull MultipartFile multipartFile) {

        String contentHash = calculateContentHash(multipartFile.getResource());
        boolean isFileExists = documentDomainService.existsByFileNameAndContentHash(multipartFile.getName(), contentHash);

        if (isFileExists)
            throw new ResourceAlreadyExistsException(
                    ResourceType.DOCUMENT,
                    "file_name = [%s], content_hash = [%s]".formatted(multipartFile.getName(), contentHash)
            );

        DocumentEntity document = documentMapper.toEntity(multipartFile, contentHash);
        documentDomainService.save(document);

    }

    @Override
    @SneakyThrows
    public void loadDocumentsFromResources() {
        List<Resource> resources = Arrays.stream(resolver.getResources("classpath:/knowledlgebase/**/*.txt"))
                .toList();

        resources.stream()
                .map(resource -> Pair.of(resource, calculateContentHash(resource)))
                .filter(p ->
                        documentDomainService.existsByFileNameAndContentHash(
                                Objects.requireNonNull(p.getFirst().getFilename()),
                                p.getSecond())
                )
                .forEach(p -> {

                    List<Document> documents = new TextReader(p.getFirst()).get();
                    TokenTextSplitter textSplitter = TokenTextSplitter.builder()
                            .withChunkSize(chunkSize)
                            .build();
                    List<Document> chunks = textSplitter.apply(documents);
                    vectorStore.accept(chunks);

                    DocumentEntity documentEntity = documentMapper.toEntity(p.getFirst(), p.getSecond());
                    documentDomainService.save(documentEntity);

                });
    }

    @SneakyThrows
    private String calculateContentHash(Resource resource) {
        return DigestUtils.md5DigestAsHex(resource.getInputStream());
    }

}
