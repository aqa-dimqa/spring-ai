package com.example.springai.domain.service.impl;

import com.example.springai.domain.enums.ResourceType;
import com.example.springai.domain.model.ChatEntity;
import com.example.springai.domain.repository.ChatRepository;
import com.example.springai.domain.service.ChatDomainService;
import com.example.springai.exception.ResourceNotFoundException;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatDomainServiceImpl implements ChatDomainService {

    private final ChatRepository chatRepository;

    @Nonnull
    @Override
    public ChatEntity save(@Nonnull final ChatEntity chat) {
        return chatRepository.save(chat);
    }

    @Nonnull
    @Override
    public ChatEntity getUserChat(@Nonnull final UUID chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.CHAT, chatId.toString()));
    }

    @Nonnull
    @Override
    public ChatEntity getUserChat(@Nonnull final UUID userId, @Nonnull final UUID chatId) {
        return chatRepository.findByUserIdAndId(userId, chatId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.CHAT, chatId.toString()));
    }

    @Nonnull
    @Override
    public List<ChatEntity> getAllUserChats(@Nonnull final UUID userId, final boolean isActive) {
        return chatRepository.findAllChatsByUserIdAndIsActiveOrderByUpdatedAtDesc(userId, isActive);
    }

    @Nonnull
    @Override
    public List<ChatEntity> getAllUserNonSpaceChats(@Nonnull final UUID userId, final boolean isActive) {
        return chatRepository.findAllChatsByUserIdAndSpaceIsNullAndIsActiveOrderByUpdatedAtDesc(userId, isActive);
    }

    @Override
    public void deleteChat(@Nonnull final UUID chatId) {
        chatRepository.findById(chatId)
                .ifPresentOrElse(
                        _ -> chatRepository.deleteById(chatId),
                        () -> {
                            throw new ResourceNotFoundException(ResourceType.CHAT, chatId.toString());
                        }
                );
    }

}
