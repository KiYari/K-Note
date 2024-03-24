package dev.kiyari.note.controller;

import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.model.note.ListDto;
import dev.kiyari.note.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/note")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<ListDto>> getAll() {
        return ResponseEntity.ok(noteService.getAll()
                .stream()
                .map(ListDto::parseObject)
                .collect(Collectors.toSet())
        );
    }

    @GetMapping()
    public ResponseEntity<ListDto> read(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(noteService.read(id))
        );
    }

    @PostMapping()
    public ResponseEntity<EditDto> create(@RequestBody EditDto dto) {
        return ResponseEntity.ok(
                EditDto.parseObject(noteService.save(dto))
        );
    }

    @PutMapping()
    public ResponseEntity<EditDto> update(@RequestBody EditDto dto) {
        return ResponseEntity.ok(
                EditDto.parseObject(noteService.update(dto))
        );
    }

    @DeleteMapping()
    public ResponseEntity<ListDto> delete(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
                ListDto.parseObject(noteService.delete(id))
        );
    }
}
