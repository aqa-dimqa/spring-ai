package com.example.springai.middleware.service;

import com.example.springai.middleware.model.request.CreateSpaceRequest;
import com.example.springai.middleware.model.response.SpaceResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public interface SpaceEdgeService {

    @Nonnull
    UUID createNewSpace(@Nonnull final CreateSpaceRequest request);

    @Nonnull
    SpaceResponse getSpace(@Nonnull final UUID spaceId);

    @Nonnull
    SpaceResponse getUserSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId);

    @Nonnull
    List<SpaceShortResponse> getAllUserSpaces(@Nonnull final UUID userId, final boolean isActive);

    void deleteSpace(@Nonnull final UUID spaceId);

    void deleteUserSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId);

    void updateSpaceTitle(UUID userId, UUID chatId, @Valid @NotBlank String title);

    void changeSpaceActiveStatus(@Nonnull final UUID userId, @Nonnull final UUID spaceId, final boolean isActive);

}
