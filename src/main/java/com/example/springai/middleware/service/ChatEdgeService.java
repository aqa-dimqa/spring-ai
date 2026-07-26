package com.example.springai.middleware.service;

import com.example.springai.middleware.model.request.ChatRequest;
import com.example.springai.middleware.model.response.ChatResponse;
import com.example.springai.middleware.model.response.ChatShortResponse;
import com.example.springai.middleware.model.response.DocumentShortResponse;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

public interface ChatEdgeService {

    @Nonnull
    UUID createNewChat(@Nonnull final ChatRequest request);

    @Nonnull
    ChatResponse getChat(@Nonnull final UUID chatId);

    @Nonnull
    ChatResponse getChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    @Nonnull
    List<ChatShortResponse> getAllUserChats(@Nonnull final UUID userId, final boolean isActive);

    void updateChatTitle(@Nonnull final UUID userId,
                         @Nonnull final UUID chatId,
                         @Nonnull final String title);

    void archiveChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    void unarchiveChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    void deleteChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    @Nonnull
    SseEmitter processMessageWithStreaming(@Nonnull final UUID chatId,
                                           @Nonnull final String prompt);
}
