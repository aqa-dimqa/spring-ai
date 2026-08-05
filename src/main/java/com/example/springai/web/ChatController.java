package com.example.springai.web;

import com.example.springai.context.UserContext;
import com.example.springai.middleware.model.request.CreateChatRequest;
import com.example.springai.middleware.model.response.ChatResponse;
import com.example.springai.middleware.model.response.ChatShortResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import com.example.springai.middleware.service.ChatEdgeService;
import com.example.springai.middleware.service.SpaceEdgeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatEdgeService chatEdgeService;
    private final SpaceEdgeService spaceEdgeService;
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
        log.info("Open Main Page");
        modelMap.addAttribute("spaces", spaceEdgeService.getAllUserSpaces(defaultUserId, true));
        modelMap.addAttribute("chats", chatEdgeService.getAllUserChats(defaultUserId, true));
        return "chat";
    }

    @PostMapping("/chat/new")
    public String newChat(@Valid @NotNull @RequestBody CreateChatRequest request) {

        log.info("Create new chat for user = [{}], space = [{}], title = [{}]",
                userContext.getUserId(), request.spaceId(), request.title());

        request = request.userId(userContext.getUserId());
        UUID newChatId = chatEdgeService.createNewChat(request);
        return "redirect:/chat/" + newChatId;
    }

    @GetMapping({"/chat/{chat_id}", "/chat/{chat_id}/"})
    public String showChat(@Valid @NotBlank @PathVariable("chat_id") final String chatIdText, ModelMap modelMap) {

        log.info("Open user chat: user = [{}], chat = [{}]", userContext.getUserId(), chatIdText);

        UUID chatId = UUID.fromString(chatIdText);
        ChatResponse chat = chatEdgeService.getChat(userContext.getUserId(), chatId);

        List<SpaceShortResponse> spaces = spaceEdgeService.getAllUserSpaces(userContext.getUserId(), true);
        List<ChatShortResponse> chats = chatEdgeService.getAllUserNonSpaceChats(userContext.getUserId(), true);

        modelMap.addAttribute("spaces", spaces);
        modelMap.addAttribute("chats", chats);
        modelMap.addAttribute("chat", chat);

        return "chat";

    }

    @PostMapping("/chat/{chat_id}/archive")
    public String archiveChat(@Valid @NotBlank @PathVariable("chat_id") String chatIdText) {
        log.info("Archive chat: user = {}, chat = {}", userContext.getUserId(), chatIdText);
        UUID chatId = UUID.fromString(chatIdText);
        chatEdgeService.archiveChat(userContext.getUserId(), chatId);
        return "redirect:/chat/";
    }

    @PostMapping("/chat/{chat_id}/restore")
    public String restoreChat(@Valid @NotBlank @PathVariable("chat_id") String chatIdText) {
        log.info("Restore chat: user = {}, chat = {}", userContext.getUserId(), chatIdText);
        UUID chatId = UUID.fromString(chatIdText);
        chatEdgeService.unarchiveChat(userContext.getUserId(), chatId);
        return "redirect:/chat/";
    }

    @PostMapping("/chat/{chat_id}/delete")
    public String deleteChat(@Valid @NotBlank @PathVariable("chat_id") String chatIdText) {
        log.info("Delete chat: user = {}, chat = {}", userContext.getUserId(), chatIdText);
        UUID chatId = UUID.fromString(chatIdText);
        chatEdgeService.deleteChat(userContext.getUserId(), chatId);
        return "redirect:/chat/";
    }

}
