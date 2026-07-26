package com.example.springai.domain.model;

import com.example.springai.constants.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
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
        name = Constants.Db.SPACE_TABLE
)
public class SpaceEntity extends BaseEntity<SpaceEntity> {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "position", nullable = false)
    private int position;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Builder.Default
    @OneToMany(mappedBy = "space")
    private List<ChatEntity> chats = new ArrayList<>();

}
