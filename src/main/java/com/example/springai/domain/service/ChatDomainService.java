package com.example.springai.domain.service;

import com.example.springai.domain.model.ChatEntity;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.UUID;

public interface ChatDomainService {

    @Nonnull
    ChatEntity save(@Nonnull final ChatEntity chatEntity);

    @Nonnull
    ChatEntity getUserChat(@Nonnull final UUID chatId);

    @Nonnull
    ChatEntity getUserChat(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    @Nonnull
    List<ChatEntity> getAllUserChats(@Nonnull final UUID userId, final boolean isActive);

    void deleteChat(@Nonnull final UUID chatId);

}
