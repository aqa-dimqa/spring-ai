package com.example.springai.domain.repository;

import com.example.springai.domain.model.SpaceEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<SpaceEntity, UUID> {

    boolean existsByUserIdAndTitleIgnoreCase(@Nonnull final UUID userId,
                                             @Nonnull final String title);

    boolean existsByUserIdAndIdNotAndTitleIgnoreCase(@Nonnull final UUID userId,
                                                     @Nonnull final UUID spaceId,
                                                     @Nonnull final String title);

    @Nonnull
    Optional<SpaceEntity> findById(@Nonnull final UUID chatId);

    @Nonnull
    Optional<SpaceEntity> findByUserIdAndId(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    @Nonnull
//    @Query("""
//            SELECT s
//            FROM SpaceEntity s
//            WHERE s.userId = :userId
//            AND s.isActive = :isActive
//            ORDER BY s.updatedAt DESC
//            """)
    List<SpaceEntity> findAllByUserIdAndIsActiveOrderByUpdatedAtDesc(UUID userId, boolean isActive);

    void deleteById(@Nonnull final UUID chatId);
}
