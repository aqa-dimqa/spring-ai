package com.example.springai.domain.model;

import com.example.springai.constants.Constants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

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

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "space", cascade = CascadeType.ALL)
    private List<ChatEntity> chats;

}
