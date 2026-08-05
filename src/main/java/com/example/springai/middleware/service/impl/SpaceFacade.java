package com.example.springai.middleware.service.impl;

import com.example.springai.domain.enums.ResourceType;
import com.example.springai.domain.model.SpaceEntity;
import com.example.springai.domain.service.SpaceDomainService;
import com.example.springai.exception.ResourceAlreadyExistsException;
import com.example.springai.middleware.mapper.SpaceMapper;
import com.example.springai.middleware.model.request.CreateSpaceRequest;
import com.example.springai.middleware.model.response.SpaceResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import com.example.springai.middleware.service.SpaceEdgeService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceFacade implements SpaceEdgeService {

    private final SpaceDomainService spaceDomainService;
    private final SpaceMapper spaceMapper;

    @Nonnull
    @Override
    public UUID createNewSpace(@Nonnull final CreateSpaceRequest request) {

        boolean spaceNameIsTaken = spaceDomainService.existsUserSpaceWithSameTitleIgnoreCase(
                request.userId(),
                request.title()
        );
        if (spaceNameIsTaken)
            throw new ResourceAlreadyExistsException(ResourceType.SPACE, "title = [" + request.title() + "]");

        SpaceEntity space = spaceDomainService.save(spaceMapper.toEntity(request));
        return space.getId();

    }

    @Nonnull
    @Override
    public SpaceResponse getSpace(@Nonnull final UUID spaceId) {
        SpaceEntity space = spaceDomainService.getSpace(spaceId);
        return spaceMapper.toResponse(space);
    }

    @Nonnull
    @Override
    public SpaceResponse getUserSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId) {
        SpaceEntity space = spaceDomainService.getUserSpace(userId, spaceId);
        return spaceMapper.toResponse(space);
    }

    @Nonnull
    @Override
    public List<SpaceShortResponse> getAllUserSpaces(@Nonnull final UUID userId, final boolean isActive) {
        List<SpaceEntity> space = spaceDomainService.getAllUserSpaces(userId, isActive);
        return spaceMapper.toShortResponse(space);
    }

    @Override
    public void deleteSpace(@Nonnull final UUID spaceId) {
        spaceDomainService.deleteSpace(spaceId);
    }

    @Override
    public void deleteUserSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId) {
        spaceDomainService.getUserSpace(userId, spaceId);
        spaceDomainService.deleteSpace(spaceId);
    }

    @Override
    public void updateSpaceTitle(@Nonnull final UUID userId,
                                 @Nonnull final UUID spaceId,
                                 @Nonnull final String title
    ) {

        boolean spaceNameIsTakenByAnotherUserSpace = spaceDomainService.existsAnotherUserSpaceWithSameTitleIgnoreCase(
                userId,
                spaceId,
                title
        );
        if (spaceNameIsTakenByAnotherUserSpace)
            throw new ResourceAlreadyExistsException(
                    ResourceType.SPACE,
                    Map.of("user id", userId,
                            "title", title
                    )
            );

        SpaceEntity space = spaceDomainService.getUserSpace(userId, spaceId);
        spaceDomainService.save(space.setTitle(title));

    }

    @Override
    public void changeSpaceActiveStatus(@Nonnull final UUID userId,
                                        @Nonnull final UUID spaceId,
                                        final boolean isActive
    ) {
        SpaceEntity space = spaceDomainService.getUserSpace(userId, spaceId);
        space.setIsActive(isActive);
        spaceDomainService.save(space);
    }

}
