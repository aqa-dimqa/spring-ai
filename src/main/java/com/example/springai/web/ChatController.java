package com.example.springai.web;

import com.example.springai.context.UserContext;
import com.example.springai.middleware.model.request.ChatRequest;
import com.example.springai.middleware.model.response.ChatResponse;
import com.example.springai.middleware.model.response.ChatShortResponse;
import com.example.springai.middleware.service.ChatEdgeService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatEdgeService chatEdgeService;
    private final UserContext userContext;

    @Value("${app.user.default.id}")
    private UUID defaultUserId;

    @Value("${app.user.default.username}")
    private String defaultUsername;

    @ModelAttribute
    public void setUserContext() {
        userContext.setUserId(defaultUserId);
        userContext.setUsername(defaultUsername);
    }

    @GetMapping({"/", "/chat", "/chat/"})
    public String mainPage(ModelMap modelMap) {
        modelMap.addAttribute("chats", chatEdgeService.getAllUserChats(defaultUserId, true));
        return "chat";
    }

    @PostMapping("/chat/new")
    public String newChat(@Valid @RequestBody ChatRequest request,
                          @RequestParam("space_id") @Nullable final String spaceIdText
    ) {
        UUID newChatId = chatEdgeService.createNewChat(request);
        return "redirect:/chat/" + newChatId;
    }

    @GetMapping({"/chat/{chat_id}", "/chat/{chat_id}/"})
    public String showChat(@Valid @NotBlank @PathVariable("chat_id") final String chatIdText, ModelMap modelMap) {

        UUID chatId = UUID.fromString(chatIdText);
        List<ChatShortResponse> chats = chatEdgeService.getAllUserChats(userContext.getUserId(), true);
        ChatResponse chat = chatEdgeService.getChat(userContext.getUserId(), chatId);

        modelMap.addAttribute("chats", chats);
        modelMap.addAttribute("chat", chat);

        return "chat";

    }

    @PostMapping("/chat/{chat_id}/archive")
    public String archiveChat(@Valid @NotBlank @PathVariable("chat_id") String chatIdText) {
        UUID chatId = UUID.fromString(chatIdText);
        chatEdgeService.archiveChat(userContext.getUserId(), chatId);
        return "redirect:/chat/";
    }

    @PostMapping("/chat/{chat_id}/unarchive")
    public String unarchiveChat(@Valid @NotBlank @PathVariable("chat_id") String chatIdText) {
        UUID chatId = UUID.fromString(chatIdText);
        chatEdgeService.unarchiveChat(userContext.getUserId(), chatId);
        return "redirect:/chat/";
    }

    @PostMapping("/chat/{chat_id}/delete")
    public String deleteChat(@Valid @NotBlank @PathVariable("chat_id") String chatIdText) {
        UUID chatId = UUID.fromString(chatIdText);
        chatEdgeService.deleteChat(userContext.getUserId(), chatId);
        return "redirect:/chat/";
    }

}
