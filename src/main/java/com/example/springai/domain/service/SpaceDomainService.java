package com.example.springai.domain.service;

import com.example.springai.domain.model.SpaceEntity;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.UUID;

public interface SpaceDomainService {

    @Nonnull
    SpaceEntity save(@Nonnull final SpaceEntity spaceEntity);

    @Nonnull
    SpaceEntity getSpace(@Nonnull final UUID spaceId);

    @Nonnull
    SpaceEntity getUserSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId);

    @Nonnull
    List<SpaceEntity> getAllUserSpaces(@Nonnull final UUID userId, final boolean isActive);

    void deleteSpace(@Nonnull final UUID spaceId);

    boolean existsUserSpaceWithSameTitleIgnoreCase(UUID userId, String title);

    boolean existsAnotherUserSpaceWithSameTitleIgnoreCase(UUID userId, UUID spaceId, String title);
}
