package com.example.springai.exception;

import com.example.springai.domain.enums.ResourceType;
import jakarta.annotation.Nonnull;
import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

    private final ResourceType resourceType;
    private final String query;

    public ResourceAlreadyExistsException(@Nonnull final ResourceType resourceType,
                                          @Nonnull final String query
    ) {
        super("Resource already exists. Resource type = [%s], query: [%s]".formatted(resourceType, query));
        this.resourceType = resourceType;
        this.query = query;
    }

}
