package dev.kiyari.note.controller;

import dev.kiyari.note.model.label.EditDto;
import dev.kiyari.note.model.label.ListDto;
import dev.kiyari.note.service.LabelService;
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
    public ResponseEntity<Set<ListDto>> getAll() {
        return ResponseEntity.ok(labelService.getAll()
                .stream()
                .map(ListDto::parseObject)
                .collect(Collectors.toSet())
        );
    }

    @GetMapping()
    public ResponseEntity<ListDto> read(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(labelService.read(id))
        );
    }

    @PostMapping()
    public ResponseEntity<EditDto> create(@RequestBody EditDto dto) {
        return ResponseEntity.ok(
                EditDto.parseObject(labelService.save(dto))
        );
    }

    @PutMapping()
    public ResponseEntity<EditDto> update(@RequestBody EditDto dto) {
        return ResponseEntity.ok(
                EditDto.parseObject(labelService.update(dto))
        );
    }

    @DeleteMapping()
    public ResponseEntity<ListDto> delete(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(labelService.delete(id))
        );
    }
}
