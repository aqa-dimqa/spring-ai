package com.example.springai.domain.model;

import com.example.springai.constants.Constants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.*;

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
        name = Constants.Db.DOCUMENT_GROUP_TABLE
)
public class DocumentGroupEntity extends BaseEntity<DocumentGroupEntity> {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            schema = Constants.Db.APP_SCHEMA,
            name =  Constants.Db.DOCUMENT_DOCUMENT_GROUP_TABLE,
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    private List<DocumentEntity> documents = new ArrayList<>();

}
