package com.example.springai.domain.repository;

import com.example.springai.domain.model.SpaceEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<SpaceEntity, UUID> {

    @Nonnull
    Optional<SpaceEntity> findById(@Nonnull final UUID spaceId);

    @Nonnull
    Optional<SpaceEntity> findByUserIdAndId(@Nonnull final UUID userId, @Nonnull final UUID spaceId);

    boolean existsByUserIdAndId(@Nonnull final UUID userId, @Nonnull final UUID id);

    @Nonnull
    //@Query("SELECT s FROM SpaceEntity s WHERE s.userId = :userId AND s.isActive = :isActive ORDER BY s.updatedAt DESC")
    List<SpaceEntity> findAllByUserIdAndIsActiveOrderByUpdatedAtDesc(@Nonnull final UUID userId, boolean isActive);

    void deleteById(@Nonnull final UUID chatId);

}
