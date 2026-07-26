package com.example.springai.middleware.service.impl;

import com.example.springai.domain.model.SpaceEntity;
import com.example.springai.domain.service.ChatDomainService;
import com.example.springai.domain.service.SpaceDomainService;
import com.example.springai.middleware.mapper.SpaceMapper;
import com.example.springai.middleware.model.response.SpaceResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import com.example.springai.middleware.model.response.UuidIdResponse;
import com.example.springai.middleware.service.SpaceEdgeService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceFacade implements SpaceEdgeService {

    private final SpaceDomainService spaceDomainService;
    private final ChatDomainService chatDomainService;
    private final SpaceMapper spaceMapper;

    @Override
    public UuidIdResponse createSpace(@Nonnull final UUID userId, @Nonnull final String title) {
        SpaceEntity space = spaceDomainService.save(
                spaceMapper.toEntity(
                        userId,
                        title
                ));
        return spaceMapper.toUUIDIdResponse(space);
    }

    @Nonnull
    @Override
    public SpaceResponse getSpace(@Nonnull final UUID spaceId) {
        SpaceEntity space = spaceDomainService.getSpaceById(spaceId);
        return spaceMapper.toResponse(space);
    }

    @Nonnull
    @Override
    public SpaceResponse getUserSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId) {
        SpaceEntity space = spaceDomainService.getSpaceByUserIdAndId(userId, spaceId);
        return spaceMapper.toResponse(space);
    }

    @Nonnull
    @Override
    public List<SpaceShortResponse> getSpaces(@Nonnull final UUID userId, final boolean isActive) {
        List<SpaceEntity> spaces = spaceDomainService.getAllUserSpacesAndActiveStatus(userId, isActive);
        return spaceMapper.toShortResponse(spaces);
    }

    @Override
    public void updateSpaceTitle(@NonNull UUID userId, @NonNull UUID spaceId, @NonNull String title) {
        SpaceEntity space = spaceDomainService.getSpaceByUserIdAndId(userId, spaceId);
        space.setTitle(title.trim());
        spaceDomainService.save(space);
    }

    @Override
    public void changeSpaceActiveStatus(@Nonnull final UUID userId, @Nonnull UUID spaceId, boolean isActive) {
        SpaceEntity space = spaceDomainService.getSpaceByUserIdAndId(userId, spaceId);
        space.setActive(isActive);
        spaceDomainService.save(space);
    }

    @Override
    public void deleteSpace(@Nonnull final UUID userId, @Nonnull UUID spaceId) {
        SpaceEntity space = spaceDomainService.getSpaceByUserIdAndId(userId, spaceId);
        space.getChats().forEach(chat ->
                chatDomainService.deleteChat(chat.getId()));
        spaceDomainService.deleteSpaceById(spaceId);
    }

}
