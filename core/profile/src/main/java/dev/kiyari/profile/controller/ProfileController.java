package dev.kiyari.profile.controller;

import dev.kiyari.profile.model.profile.EditDto;
import dev.kiyari.profile.model.profile.ListDto;
import dev.kiyari.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping()
    @Operation(summary = "Get one profile by id", description = "Retrieves one note by id")
    public ResponseEntity<ListDto> read(
            @Parameter(name = "id", description = "ID of note", required = true, example = "5")
            @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(profileService.read(id))
        );
    }

    @PostMapping()
    @Operation(summary = "Create note", description = "Creates new Profile and retrieves it back")
    public ResponseEntity<ListDto> create(
            @Parameter(name = "dto", description = "Transferable object for editing Profile model", required = true)
            @Schema(implementation = EditDto.class)
            @RequestBody EditDto dto) {
        return ResponseEntity.ok(
                ListDto.parseObject(profileService.save(dto))
        );
    }

    @PutMapping()
    @Operation(summary = "Update note", description = "Updates existing Profile and retrieves updated label")
    public ResponseEntity<ListDto> update(
            @Parameter(name = "id", description = "ID of Profile", required = true, example = "5")
            @RequestParam(name = "id") Long id,
            @Parameter(name = "dto", description = "Transferable object for editing Profile model", required = true)
            @Schema(implementation = EditDto.class)
            @RequestBody EditDto dto) {
        return ResponseEntity.ok(
                ListDto.parseObject(profileService.update(id, dto))
        );
    }

    @DeleteMapping()
    @Operation(summary = "Delete Profile", description = "Deletes Profile by id and retrieves that was deleted")
    public ResponseEntity<ListDto> delete(
            @Parameter(name = "id", description = "ID of Profile", required = true, example = "5")
            @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(profileService.delete(id))
        );
    }
}
