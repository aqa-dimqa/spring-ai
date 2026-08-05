package com.example.springai.middleware.mapper;

import com.example.springai.domain.model.BaseEntity;
import com.example.springai.middleware.model.response.UuidIdResponse;
import jakarta.annotation.Nonnull;

public abstract class BaseMapper {

    @Nonnull
    public <T extends BaseEntity<T>> UuidIdResponse toUuidIdMapper(@Nonnull final T entity) {
        return new UuidIdResponse(entity.getId());
    }

}
