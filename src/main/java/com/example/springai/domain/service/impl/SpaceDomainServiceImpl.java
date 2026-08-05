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
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpaceDomainServiceImpl implements SpaceDomainService {

    private final SpaceRepository spaceRepository;

    @Nonnull
    @Override
    public SpaceEntity save(@Nonnull final SpaceEntity spaceEntity) {
        return spaceRepository.save(spaceEntity);
    }

    @Nonnull
    @Override
    public SpaceEntity getSpace(@Nonnull final UUID spaceId) {
        return spaceRepository.findById(spaceId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.SPACE, "space id = [" + spaceId + "]"));
    }

    @Nonnull
    @Override
    public SpaceEntity getUserSpace(@Nonnull final UUID userId, @Nonnull final UUID spaceId) {
        return spaceRepository.findByUserIdAndId(userId, spaceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                ResourceType.SPACE,
                                Map.of(
                                        "user id", userId,
                                        "space id", spaceId)
                        )
                );
    }

    @Nonnull
    @Override
    public List<SpaceEntity> getAllUserSpaces(@Nonnull final UUID userId, boolean isActive) {
        return spaceRepository.findAllByUserIdAndIsActiveOrderByUpdatedAtDesc(userId, isActive);
    }

    @Override
    public void deleteSpace(@Nonnull final UUID spaceId) {
        spaceRepository.deleteById(spaceId);
    }

    @Override
    public boolean existsUserSpaceWithSameTitleIgnoreCase(UUID userId, String title) {
        return spaceRepository.existsByUserIdAndTitleIgnoreCase(userId, title);
    }

    @Override
    public boolean existsAnotherUserSpaceWithSameTitleIgnoreCase(UUID userId, UUID spaceId, String title) {
        return spaceRepository.existsByUserIdAndIdNotAndTitleIgnoreCase(userId, spaceId, title);
    }

}
