package com.example.springai.web;

import com.example.springai.context.UserContext;
import com.example.springai.middleware.model.response.SpaceResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import com.example.springai.middleware.service.ChatEdgeService;
import com.example.springai.middleware.service.SpaceEdgeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Service
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/")
public class RestSpaceController {

    private final SpaceEdgeService spaceEdgeService;
    private final UserContext userContext;
    private final ChatEdgeService chatEdgeService;

    @Value("${app.user.default.id}")
    private UUID defaultUserId;

    @Value("${app.user.default.username}")
    private String defaultUsername;

    @ModelAttribute
    public void setUserContext() {
        userContext.setUserId(defaultUserId);
        userContext.setUsername(defaultUsername);
    }

    @GetMapping(value = {"/{space_id}", "/{space_id}/"})
    public ResponseEntity<SpaceResponse> getSpace(@Valid @NotBlank @PathVariable("space_id") final String spaceIdText) {
        UUID spaceId = UUID.fromString(spaceIdText);
        SpaceResponse chat = spaceEdgeService.getUserSpace(userContext.getUserId(), spaceId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(chat);
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<SpaceShortResponse>> getSpaces(@RequestParam("is_active") boolean isActive) {
        List<SpaceShortResponse> archivedChats = spaceEdgeService.getSpaces(defaultUserId, isActive);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(archivedChats);
    }

    @PatchMapping(value = {"/{space_id}/title", "/{space_id}/title/"})
    public void updateSpaceTitle(@Valid @NotBlank @PathVariable("space_id") final String spaceIdText,
                                 @Valid @NotBlank @RequestParam("title") final String title
    ) {
        UUID spaceId = UUID.fromString(spaceIdText);
        spaceEdgeService.updateSpaceTitle(userContext.getUserId(), spaceId, title);
    }

    @GetMapping(value = {"/{space_id}/archive", "/{space_id}/archive/"})
    public void archiveSpace(@Valid @NotBlank @PathVariable("space_id") final String spaceIdText) {
        UUID spaceId = UUID.fromString(spaceIdText);
        spaceEdgeService.changeSpaceActiveStatus(userContext.getUserId(), spaceId, true);
    }

    @GetMapping(value = {"/{space_id}/restore", "/{space_id}/restore/"})
    public void restoreSpace(@Valid @NotBlank @PathVariable("space_id") final String spaceIdText) {
        UUID spaceId = UUID.fromString(spaceIdText);
        spaceEdgeService.changeSpaceActiveStatus(userContext.getUserId(), spaceId, false);
    }

    @DeleteMapping(value = {"/{space_id}", "/{space_id}/"})
    public void deleteSpace(@Valid @NotBlank @PathVariable("space_id") final String spaceIdText) {
        UUID spaceId = UUID.fromString(spaceIdText);
        spaceEdgeService.deleteSpace(userContext.getUserId(), spaceId);
    }

}
