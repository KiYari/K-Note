package dev.kiyari.note.controller;

import dev.kiyari.note.model.label.EditDto;
import dev.kiyari.note.model.label.ListDto;
import dev.kiyari.note.service.LabelService;
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
@RequestMapping("/api/label")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @GetMapping("/all")
    @Operation(summary = "Get all labels", description = "Retrieves a set of all available labels")
    public ResponseEntity<Set<ListDto>> getAll() {
        return ResponseEntity.ok(labelService.getAll()
                .stream()
                .map(ListDto::parseObject)
                .collect(Collectors.toSet())
        );
    }

    @GetMapping()
    @Operation(summary = "Get one label by id", description = "Retrieves one label by id")
    public ResponseEntity<ListDto> read(
            @Parameter(name = "id", description = "ID of label", required = true, example = "5")
            @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(labelService.read(id))
        );
    }

    @PostMapping()
    @Operation(summary = "Create label", description = "Creates new label and retrieves it back")
    public ResponseEntity<EditDto> create(
            @Parameter(name = "dto", description = "Transferable object for editing model", required = true)
            @Schema(implementation = dev.kiyari.note.model.note.EditDto.class)
            @RequestBody EditDto dto) {
        return ResponseEntity.ok(
                EditDto.parseObject(labelService.save(dto))
        );
    }

    @PutMapping()
    @Operation(summary = "Update label", description = "Updates existing label and retrieves updated label")
    public ResponseEntity<EditDto> update(
            @Parameter(name = "id", description = "ID of label", required = true, example = "5")
            @RequestParam(name = "id") Long id,

            @Parameter(name = "dto", description = "Transferable object for editing model", required = true)
            @Schema(implementation = dev.kiyari.note.model.note.EditDto.class)
            @RequestBody EditDto dto) {
        return ResponseEntity.ok(
                EditDto.parseObject(labelService.update(id, dto))
        );
    }

    @DeleteMapping()
    @Operation(summary = "Delete label", description = "Deletes label by id and retrieves that was deleted")
    public ResponseEntity<ListDto> delete(
            @Parameter(name = "id", description = "ID of label", required = true, example = "5")
            @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(labelService.delete(id))
        );
    }
}
