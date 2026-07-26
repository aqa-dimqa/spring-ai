package com.example.springai.middleware.service;

import com.example.springai.middleware.model.response.SpaceResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import com.example.springai.middleware.model.response.UuidIdResponse;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.UUID;

public interface SpaceEdgeService {

    UuidIdResponse createSpace(@Nonnull final UUID userId, @Nonnull final String title);

    @Nonnull
    SpaceResponse getSpace(@Nonnull final UUID spaceId);

    @Nonnull
    SpaceResponse getUserSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId);

    @Nonnull
    List<SpaceShortResponse> getSpaces(@Nonnull final UUID userId, final boolean isActive);

    void updateSpaceTitle(@Nonnull final UUID userId, @Nonnull final UUID spaceId, @Nonnull final String title);

    void changeSpaceActiveStatus(@Nonnull final UUID userId, @Nonnull final UUID spaceId, final boolean isActive);

    void deleteSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId);

}
