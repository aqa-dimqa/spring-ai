package com.example.springai.domain.service;

import com.example.springai.domain.model.SpaceEntity;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.UUID;

public interface SpaceDomainService {

    @Nonnull
    SpaceEntity save(@Nonnull final SpaceEntity space);

    @Nonnull
    SpaceEntity getSpaceById(@Nonnull final UUID spaceId);

    @Nonnull
    SpaceEntity getSpaceByUserIdAndId(@Nonnull final UUID userId, @Nonnull final UUID spaceId);

    boolean existSpaceByUserIdAndId(@Nonnull final UUID userId, @Nonnull final UUID spaceId);

    @Nonnull
    List<SpaceEntity> getAllUserSpacesAndActiveStatus(@Nonnull final UUID userId, final boolean isActive);

    void deleteSpaceById(@Nonnull final UUID spaceId);

}
