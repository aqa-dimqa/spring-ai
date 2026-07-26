package com.example.springai.web;

import com.example.springai.middleware.mapper.DocumentMapper;
import com.example.springai.middleware.service.DocumentEdgeService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/api/document", "/api/document/"})
@RequiredArgsConstructor
public class RestDocumentController {

    private final DocumentEdgeService documentEdgeService;
    private final DocumentMapper documentMapper;

    @Nonnull
    @PostMapping("/")
    public ResponseEntity<Void> addDocument(@Valid @NotNull @RequestPart("file") final MultipartFile multipartFile) {
        documentEdgeService.addNewDocument(multipartFile);
        return ResponseEntity.ok()
                .build();
    }


}
