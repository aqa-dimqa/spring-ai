package com.example.springai.domain.service.impl;

import com.example.springai.domain.enums.ResourceType;
import com.example.springai.domain.model.SpaceEntity;
import com.example.springai.domain.repository.SpaceRepository;
import com.example.springai.domain.service.SpaceDomainService;
import com.example.springai.exception.ResourceNotFoundException;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpaceDomainServiceImpl implements SpaceDomainService {

    private final SpaceRepository spaceRepository;

    @Nonnull
    public SpaceEntity save(@Nonnull final SpaceEntity space) {
        return spaceRepository.save(space);
    }

    @Nonnull
    public SpaceEntity getSpaceById(@Nonnull final UUID spaceId) {
        return spaceRepository.findById(spaceId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.SPACE, spaceId.toString()));
    }

    @Nonnull
    public SpaceEntity getSpaceByUserIdAndId(@Nonnull final UUID userId, @Nonnull final UUID spaceId) {
        return spaceRepository.findByUserIdAndId(userId, spaceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResourceType.SPACE,
                        "userId = [%s], spaceId = [%s]".formatted(userId.toString(), spaceId.toString()))
                );
    }

    public boolean existSpaceByUserIdAndId(@Nonnull final UUID userId, @Nonnull final UUID spaceId) {
        return spaceRepository.existsByUserIdAndId(spaceId, userId);
    }

    @Nonnull
    public List<SpaceEntity> getAllUserSpacesAndActiveStatus(@Nonnull final UUID userId, final boolean isActive) {
        return spaceRepository.findAllUserSpacesAndActiveStatus(userId, isActive);
    }

    public void deleteSpaceById(@Nonnull final UUID spaceId) {
        spaceRepository.deleteById(spaceId);
    }

}
