package com.example.springai.domain.model;

import com.example.springai.constants.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(
        schema = Constants.Db.APP_SCHEMA,
        name = Constants.Db.DOCUMENT_TABLE
)
public class DocumentEntity extends BaseEntity<DocumentEntity> {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_hash", nullable = false)
    private String contentHash;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "chunk_count")
    private int chunkNumber;

    @Column(name = "size", nullable = false)
    private long size;

}
