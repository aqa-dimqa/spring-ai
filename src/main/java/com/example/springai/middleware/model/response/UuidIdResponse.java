package com.example.springai.middleware.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public record UuidIdResponse(
        @JsonProperty("id")
        UUID id
) implements Serializable {
}
