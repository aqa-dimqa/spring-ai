package com.example.springai.web;

import com.example.springai.context.UserContext;
import com.example.springai.middleware.model.request.CreateSpaceRequest;
import com.example.springai.middleware.model.response.SpaceResponse;
import com.example.springai.middleware.model.response.SpaceShortResponse;
import com.example.springai.middleware.service.SpaceEdgeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space")
public class RestSpaceController {

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

    @PostMapping(value = {"/new", "/new/"})
    public ResponseEntity<UUID> createNewSpace(@Valid @NotNull final CreateSpaceRequest request) {

        UUID spaceId = spaceEdgeService.createNewSpace(request.userId(userContext.getUserId()));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(spaceId);

    }

    @GetMapping(value = {"/{space_id}", "/{space_id}/"})
    public ResponseEntity<SpaceResponse> getUserSpace(@Valid @NotBlank @PathVariable("space_id") final String spaceIdText) {
        UUID spaceId = UUID.fromString(spaceIdText);
        SpaceResponse space = spaceEdgeService.getUserSpace(userContext.getUserId(), spaceId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(space);
    }

    @GetMapping(value = {"/archive", "/archive/"})
    public ResponseEntity<List<SpaceShortResponse>> getArchivedSpaces() {
        List<SpaceShortResponse> archivedSpaces = spaceEdgeService.getAllUserSpaces(defaultUserId, false);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(archivedSpaces);
    }

    @PatchMapping(value = {"/{space_id}/title", "/{space_id}/title/"})
    public void updateSpaceTitle(@Valid @NotBlank @PathVariable("space_id") final String spaceIdText,
                                 @Valid @NotBlank @RequestParam("title") final String title
    ) {
        UUID spaceId = UUID.fromString(spaceIdText);
        spaceEdgeService.updateSpaceTitle(userContext.getUserId(), spaceId, title);
    }

}
