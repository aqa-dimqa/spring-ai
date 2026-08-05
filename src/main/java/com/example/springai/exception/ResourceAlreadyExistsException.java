package com.example.springai.exception;

import com.example.springai.domain.enums.ResourceType;
import jakarta.annotation.Nonnull;
import lombok.Getter;

import java.util.Map;

@Getter
public class ResourceAlreadyExistsException extends ResourceConditionException {

    private static final String BASE_MESSAGE = "Resource already exists";

    public ResourceAlreadyExistsException(ResourceType resourceType, String query) {
        super(BASE_MESSAGE, resourceType, query);
    }

    public ResourceAlreadyExistsException(@Nonnull final ResourceType resourceType,
                                          @Nonnull final Map<String, Object> queries
    ) {
        super(BASE_MESSAGE, resourceType, queries);
    }

}
