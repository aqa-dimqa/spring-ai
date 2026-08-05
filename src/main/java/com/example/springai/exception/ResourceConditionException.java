package com.example.springai.exception;

import com.example.springai.domain.enums.ResourceType;
import jakarta.annotation.Nonnull;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public abstract class ResourceConditionException extends RuntimeException {

    private final ResourceType resourceType;
    private final String query;

    protected ResourceConditionException(String baseMessage, ResourceType resourceType, String queryMessage) {
        super("### %s. Resource type = [%s], query: %s".formatted(baseMessage, resourceType, queryMessage));
        this.resourceType = resourceType;
        this.query = queryMessage;
    }

    protected ResourceConditionException(@Nonnull final String baseMessage,
                                         @Nonnull final ResourceType resourceType,
                                         @Nonnull final Map<String, Object> queries
    ) {
        String query = queries.entrySet().stream()
                .map(entry -> "%s = [%s]".formatted(entry.getKey(), entry.getValue().toString()))
                .collect(Collectors.joining(", "));
        super("### %s. Resource type = [%s], query: %s".formatted(baseMessage, resourceType, query));
        this.resourceType = resourceType;
        this.query = query;
    }

}
