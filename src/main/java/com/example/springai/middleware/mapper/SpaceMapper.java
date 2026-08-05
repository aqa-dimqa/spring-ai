package com.example.springai.middleware.mapper;

import com.example.springai.domain.model.SpaceEntity;
import com.example.springai.middleware.model.request.CreateSpaceRequest;
import com.example.springai.middleware.model.response.SpaceResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpaceMapper extends BaseMapper {

    private final ChatMapper chatMapper;

    @Nonnull
    public SpaceEntity toEntity(@Nonnull final CreateSpaceRequest request) {
        return SpaceEntity.builder()
                .userId(request.userId())
                .title(request.title())
                .build();
    }

    @Nonnull
    public SpaceShortResponse toShortResponse(@Nonnull final SpaceEntity space) {
        return SpaceShortResponse.builder()
                .id(space.getId())
                .title(space.getTitle())
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
                .isActive(space.getIsActive())
                .chats(chatMapper.toShortResponse(space.getChats()))
                .createdAt(space.getCreatedAt())
                .updatedAt(space.getUpdatedAt())
                .build();
    }

    @Nonnull
    public List<SpaceResponse> toResponse(@Nonnull final List<SpaceEntity> spaces) {
        return spaces.stream()
                .map(this::toResponse)
                .toList();
    }

}
