package com.example.springai.middleware.service.impl;

import com.example.springai.domain.model.ChatEntity;
import com.example.springai.domain.model.SpaceEntity;
import com.example.springai.domain.service.ChatDomainService;
import com.example.springai.domain.service.SpaceDomainService;
import com.example.springai.middleware.mapper.ChatMapper;
import com.example.springai.middleware.mapper.DocumentMapper;
import com.example.springai.middleware.model.request.ChatRequest;
import com.example.springai.middleware.model.response.ChatResponse;
import com.example.springai.middleware.model.response.ChatShortResponse;
import com.example.springai.middleware.model.response.DocumentShortResponse;
import com.example.springai.middleware.service.ChatEdgeService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatFacade implements ChatEdgeService {

    private final ChatDomainService chatDomainService;
    private final SpaceDomainService spaceDomainService;
    private final ChatMapper chatMapper;
    private final DocumentMapper documentMapper;
    private final ChatClient chatClient;

    @Nonnull
    @Override
    public UUID createNewChat(@Nonnull final ChatRequest chatRequest) {

        SpaceEntity space = null;
        if (chatRequest.spaceId() != null)
            space = spaceDomainService.getSpaceById(chatRequest.spaceId());

        ChatEntity newChat = chatDomainService.save(chatMapper.toEntity(chatRequest, space));
        return newChat.getId();

    }

    @Nonnull
    @Override
    public ChatResponse getChat(@Nonnull final UUID chatId) {
        ChatEntity chat = chatDomainService.getUserChat(chatId);
        return chatMapper.toResponse(chat);
    }

    @Nonnull
    @Override
    public ChatResponse getChat(@Nonnull final UUID userId, @Nonnull final UUID chatId) {
        ChatEntity chat = chatDomainService.getUserChat(userId, chatId);
        return chatMapper.toResponse(chat);
    }

    @Nonnull
    @Override
    public List<ChatShortResponse> getAllUserChats(@Nonnull final UUID userId, final boolean isActive) {
        List<ChatEntity> chats = chatDomainService.getAllUserChats(userId, isActive);
        return chatMapper.toShortResponse(chats);
    }

    @Override
    public void updateChatTitle(@Nonnull final UUID userId, @Nonnull final UUID chatId, @Nonnull final String title) {
        ChatEntity chat = chatDomainService.getUserChat(userId, chatId);
        chatDomainService.save(chat.setTitle(title));
    }

    @Override
    public void archiveChat(@Nonnull final UUID userId, @Nonnull final UUID chatId) {
        ChatEntity chat = chatDomainService.getUserChat(userId, chatId);
        chatDomainService.save(chat.setIsActive(false));
    }

    @Override
    public void unarchiveChat(@Nonnull final UUID userId, @Nonnull final UUID chatId) {
        ChatEntity chat = chatDomainService.getUserChat(userId, chatId);
        chatDomainService.save(chat.setIsActive(true));
    }

    @Override
    public void deleteChat(@Nonnull final UUID userId, @Nonnull final UUID chatId) {
        chatDomainService.deleteChat(chatId);
    }

    @Nonnull
    @Override
    public List<DocumentShortResponse> getChatDocuments(@Nonnull UUID userId, @Nonnull UUID chatId) {
        ChatEntity chat = chatDomainService.getUserChat(userId, chatId);
        return documentMapper.toShortResponse(chat.getDocuments());
    }

    @Nonnull
    @Override
    public SseEmitter processMessageWithStreaming(@Nonnull final UUID userId, @Nonnull UUID chatId, @Nonnull String prompt) {

        StringBuilder answer = new StringBuilder();
        SseEmitter sseEmitter = new SseEmitter(0L);
        chatDomainService.getUserChat(userId, chatId); // Check chat exist by userId and id

        chatClient.prompt(prompt)
                .advisors(advisorSpec ->
                        advisorSpec.param(ChatMemory.CONVERSATION_ID, chatId)
                )
                .stream()
                .chatResponse()
                .subscribe(
                        response -> processToken(response, sseEmitter, answer),
                        sseEmitter::completeWithError
                );

        return sseEmitter;

    }

    @SneakyThrows
    private static void processToken(@Nonnull final org.springframework.ai.chat.model.ChatResponse response,
                                     @Nonnull final SseEmitter sseEmitter,
                                     @Nonnull final StringBuilder answer
    ) {
        AssistantMessage token = Objects.requireNonNull(response.getResult()).getOutput();
        answer.append(token.getText());
        sseEmitter.send(token);
    }

}
