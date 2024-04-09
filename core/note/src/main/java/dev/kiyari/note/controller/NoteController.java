package dev.kiyari.note.controller;

import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.model.note.ListDto;
import dev.kiyari.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/all")
    @Operation(summary = "Get all notes", description = "Retrieves a set of all available notes")
    public ResponseEntity<Set<ListDto>> getAll() {
        return ResponseEntity.ok(noteService.getAll()
                .stream()
                .map(ListDto::parseObject)
                .collect(Collectors.toSet())
        );
    }

    @GetMapping()
    @Operation(summary = "Get one note by id", description = "Retrieves one note by id")
    public ResponseEntity<ListDto> read(
            @Parameter(name = "id", description = "ID of note", required = true, example = "5")
            @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(noteService.read(id))
        );
    }

    @PostMapping()
    @Operation(summary = "Create note", description = "Creates new note and retrieves it back")
    public ResponseEntity<ListDto> create(
            @Parameter(name = "dto", description = "Transferable object for editing Note model", required = true)
            @Schema(implementation = EditDto.class)
            @RequestBody EditDto dto) {
        return ResponseEntity.ok(
                ListDto.parseObject(noteService.save(dto))
        );
    }

    @PutMapping()
    @Operation(summary = "Update note", description = "Updates existing note and retrieves updated label")
    public ResponseEntity<ListDto> update(
            @Parameter(name = "id", description = "ID of note", required = true, example = "5")
            @RequestParam(name = "id") Long id,
            @Parameter(name = "dto", description = "Transferable object for editing Note model", required = true)
            @Schema(implementation = EditDto.class)
            @RequestBody EditDto dto) {
        return ResponseEntity.ok(
                ListDto.parseObject(noteService.update(id, dto))
        );
    }

    @DeleteMapping()
    @Operation(summary = "Delete note", description = "Deletes note by id and retrieves that was deleted")
    public ResponseEntity<ListDto> delete(
            @Parameter(name = "id", description = "ID of note", required = true, example = "5")
            @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(noteService.delete(id))
        );
    }

    @PostMapping("/relatedLabel")
    @Operation(summary = "Add related label", description = "Adds new related label to note")
    public ResponseEntity<ListDto> addRelatedLabel(
            @Parameter(name = "id", description = "ID of note", required = true, example = "5")
            @RequestParam(name = "id") Long id,

            @Parameter(name = "dto", description = "Transferable object that represents Label", required = true)
            @Schema(implementation = dev.kiyari.note.model.label.ListDto.class)
            @RequestBody dev.kiyari.note.model.label.ListDto dto) {

        return ResponseEntity.ok(
                ListDto.parseObject(noteService.addRelatedLabel(id, dto))
        );
    }

    @DeleteMapping("/relatedLabel")
    @Operation(summary = "Delete related label", description = "Deletes existing related label on note")
    public ResponseEntity<ListDto> deleteRelatedLabel(
            @Parameter(name = "id", description = "ID of note", required = true, example = "5")
            @RequestParam(name = "id") Long id,

            @Parameter(name = "dto", description = "Transferable object that represents Label", required = true)
            @Schema(implementation = dev.kiyari.note.model.label.ListDto.class)
            @RequestBody dev.kiyari.note.model.label.ListDto dto) {

        return ResponseEntity.ok(
                ListDto.parseObject(noteService.removeRelatedLabel(id, dto))
        );
    }
}
