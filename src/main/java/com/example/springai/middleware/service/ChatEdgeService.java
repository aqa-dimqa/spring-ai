package com.example.springai.middleware.service;

import com.example.springai.middleware.model.request.CreateChatRequest;
import com.example.springai.middleware.model.response.ChatResponse;
import com.example.springai.middleware.model.response.ChatShortResponse;
import jakarta.annotation.Nonnull;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

public interface ChatEdgeService {

    @Nonnull
    UUID createNewChat(@Nonnull final CreateChatRequest request);

    @Nonnull
    ChatResponse getChat(@Nonnull final UUID chatId);

    @Nonnull
    ChatResponse getChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    @Nonnull
    List<ChatShortResponse> getAllUserChats(@Nonnull final UUID userId, final boolean isActive);

    @Nonnull
    List<ChatShortResponse> getAllUserNonSpaceChats(@Nonnull final UUID userId, final boolean isActive);

    void updateChatTitle(@Nonnull final UUID userId,
                         @Nonnull final UUID chatId,
                         @Nonnull final String title);

    void archiveChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    void unarchiveChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    void deleteChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    @Nonnull
    SseEmitter processMessageWithStreaming(@Nonnull final UUID userId,
                                           @Nonnull final UUID chatId,
                                           @Nonnull final String prompt);
}
