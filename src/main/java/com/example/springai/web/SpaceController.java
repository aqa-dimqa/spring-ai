package com.example.springai.web;

import com.example.springai.context.UserContext;
import com.example.springai.middleware.model.request.CreateSpaceRequest;
import com.example.springai.middleware.service.SpaceEdgeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping({"/space", "/space/"})
public class SpaceController {

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

    @PostMapping({"/new", "/new/"})
    public String newSpace(@Valid @NotBlank @RequestParam("title") String title) {
        UUID userId = defaultUserId;
        CreateSpaceRequest request = CreateSpaceRequest.builder()
                .userId(userId)
                .title(title)
                .build();
        UUID newSpaceId = spaceEdgeService.createNewSpace(request);
        return "redirect:/chat/" + newSpaceId;
    }

    @PostMapping({"/{space_id}/archive", "/{space_id}/archive/"})
    public String archiveSpace(@Valid @NotBlank @PathVariable("space_id") String spaceIdText) {
        UUID spaceId = UUID.fromString(spaceIdText);
        spaceEdgeService.changeSpaceActiveStatus(userContext.getUserId(), spaceId, false);
        return "redirect:/chat/";
    }

    @PostMapping({"/{space_id}/restore", "/{space_id}/restore/"})
    public String restoreSpace(@Valid @NotBlank @PathVariable("space_id") String chatIdText) {
        UUID chatId = UUID.fromString(chatIdText);
        spaceEdgeService.changeSpaceActiveStatus(userContext.getUserId(), chatId, true);
        return "redirect:/chat/";
    }

    @PostMapping({"/space/{space_id}/delete", "/{space_id}/delete/"})
    public String deleteChat(@Valid @NotBlank @PathVariable("space_id") String spaceIdText) {
        UUID spaceId = UUID.fromString(spaceIdText);
        spaceEdgeService.deleteUserSpace(userContext.getUserId(), spaceId);
        return "redirect:/chat/";
    }

}
