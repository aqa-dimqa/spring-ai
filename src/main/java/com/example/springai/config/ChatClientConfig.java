package com.example.springai.config;

import com.example.springai.domain.service.ChatDomainService;
import com.example.springai.domain.service.ChatMessageDomainService;
import com.example.springai.middleware.mapper.ChatMessageMapper;
import com.example.springai.middleware.service.LocalizationService;
import com.example.springai.middleware.service.PostgresChatMemory;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ChatClientConfig {

    private static final String PATH_TO_PROMPT_TEMPLATE_TRANSLATION = "ai.prompt.template";
    private static final String DEFAULT_PROMPT_TEMPLATE = """
            
            {query}
            
            Context information is below, surrounded by ---------------------
            
            ---------------------
            {question_answer_context}
            ---------------------
            
            Given the context and provided history information and not prior knowledge,
            reply to the user comment. If the answer is not in the context, inform
            the user that you can't answer the question.
            """;

    private final ChatDomainService chatDomainService;
    private final ChatMessageDomainService chatMessageDomainService;
    private final ChatMessageMapper chatMessageMapper;
    private final VectorStore vectorStore;
    private final String promptTemplate;
    private final LocalizationService localizationService;

    @Autowired
    public ChatClientConfig(
            ChatDomainService chatDomainService,
            ChatMessageDomainService chatMessageDomainService,
            ChatMessageMapper chatMessageMapper,
            VectorStore vectorStore,
            LocalizationService localizationService
    ) {
        this.chatDomainService = chatDomainService;
        this.chatMessageDomainService = chatMessageDomainService;
        this.chatMessageMapper = chatMessageMapper;
        this.vectorStore = vectorStore;
        this.localizationService = localizationService;

        promptTemplate = localizationService.getTranslate(PATH_TO_PROMPT_TEMPLATE_TRANSLATION, DEFAULT_PROMPT_TEMPLATE);

    }

    @Value("${app.ai.max-message-history}")
    private int maxMessages;


    @Bean
    public ChatClient chatClient(@Nonnull final ChatClient.Builder builder) {
        return builder
                .defaultAdvisors(
                        getHistoryAdvisor(),
                        getRagAdvisor() // <-- Rag advisor should be after history advisor
                )
                .build();
    }


    private Advisor getHistoryAdvisor() {
        return MessageChatMemoryAdvisor.builder(getChatMemory())
                .build();
    }

    private Advisor getRagAdvisor() {
        return QuestionAnswerAdvisor.builder(vectorStore)
                .promptTemplate(
                        new PromptTemplate(Objects.requireNonNull(promptTemplate))
                )
                /*
                .searchRequest(
                        SearchRequest.builder()
                                .topK(4)
                                .build()
                )
                */
                .build();
    }


    private ChatMemory getChatMemory() {
        return PostgresChatMemory.builder()
                .chatDomainService(chatDomainService)
                .chatMessageDomainService(chatMessageDomainService)
                .chatMessageMapper(chatMessageMapper)
                .maxMessages(maxMessages)
                .build();
    }

}
