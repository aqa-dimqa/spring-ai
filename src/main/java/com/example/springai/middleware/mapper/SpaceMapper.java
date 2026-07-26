package com.example.springai.middleware.mapper;

import com.example.springai.domain.model.SpaceEntity;
import com.example.springai.middleware.model.response.SpaceResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import com.example.springai.middleware.model.response.UuidIdResponse;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SpaceMapper {

    private final ChatMapper chatMapper;

    @Nonnull
    public SpaceEntity toEntity(@Nonnull final UUID userId, @Nonnull final String title) {
        return SpaceEntity.builder()
                .userId(userId)
                .title(title)
                .build();
    }

    @Nonnull
    public UuidIdResponse toUUIDIdResponse(@Nonnull final SpaceEntity space) {
        return new UuidIdResponse(space.getId());
    }

    @Nonnull
    public SpaceShortResponse toShortResponse(@Nonnull final SpaceEntity space) {
        return SpaceShortResponse.builder()
                .id(space.getId())
                .title(space.getTitle())
                .chats(chatMapper.toShortResponse(space.getChats()))
                .updatedAt(space.getUpdatedAt())
                .build();
    }

    @Nonnull
    public List<SpaceShortResponse> toShortResponse(@Nonnull final List<SpaceEntity> spaces) {
        return spaces.stream()
                .map(this::toShortResponse)
                .toList();
    }

    @Nonnull
    public SpaceResponse toResponse(@Nonnull final SpaceEntity space) {
        return SpaceResponse.builder()
                .id(space.getId())
                .userId(space.getUserId())
                .title(space.getTitle())
                .position(space.getPosition())
                .chats(chatMapper.toShortResponse(space.getChats()))
                .createdAt(space.getCreatedAt())
                .updatedAt(space.getUpdatedAt())
                .build();
    }

}
