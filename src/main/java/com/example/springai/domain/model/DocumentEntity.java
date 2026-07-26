package com.example.springai.domain.model;

import com.example.springai.constants.Constants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name="file_name", nullable = false)
    private String fileName;

    @Column(name="content_hash", nullable = false)
    private String contentHash;

    @Column(name="document_type", nullable = false)
    private String documentType;

    @Column(name = "chunk_count")
    private int chunkNumber;

    @Builder.Default
    @ManyToMany(mappedBy = "documents")
    private List<DocumentGroupEntity> groups = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "documents")
    private List<ChatEntity> chats = new ArrayList<>();

}
