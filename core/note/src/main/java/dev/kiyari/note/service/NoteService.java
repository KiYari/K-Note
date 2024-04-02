package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.note.ListDto;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.repository.NoteRepository;
import dev.kiyari.note.util.exception.DeleteEntityException;
import dev.kiyari.note.util.exception.SaveEntityException;
import dev.kiyari.note.util.exception.UnexpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final LabelService labelService;

    public Note read(Long id) {
        return noteRepository.findById(id).orElseThrow();
    }

    public Set<Note> getAll() {
        Set<Note> notes = new LinkedHashSet<>();

        noteRepository.findAll().forEach(notes::add);

        return notes;
    }

    public Note save(EditDto note) {
        if (note != null) {
            note.setDateCreated(LocalDateTime.now());
            note.setLastUpdated(LocalDateTime.now());
            return noteRepository.save(Note.parseEditDto(note));
        }
        throw new UnexpectedException("Could not save Note due to unexpected reasons.");
    }

    public Note update(Long id, EditDto dto) {
        if (!existsById(id)) {
            throw new SaveEntityException("There is no entity with such ID to update");
        }
        Note existing = read(id);

        existing.setNote(dto.getNote());
        existing.setLastUpdated(LocalDateTime.now());
        existing.setDescription(dto.getDescription());
        existing.setTitle(dto.getTitle());

        return noteRepository.save(existing);
    }

    public Note delete(Long id) {
        if(existsById(id)) {
            Note note = read(id);
            noteRepository.delete(note);
            return note;
        }
        throw new DeleteEntityException("Could not delete note due to unexpected reasons.");
    }

    public Boolean existsById(Long id) {
        if (id == null || id < 0) {
            return false;
        }
        return noteRepository.existsById(id);
    }

    public Boolean isLabelPresentsInNoteRelatedLabels(Label label, Note note) {
        return note.getRelatedLabels().contains(label);
    }

    public Note addRelatedLabel(Long id, dev.kiyari.note.model.label.ListDto dto) {
        if (!labelService.existsById(dto.getId()) || !labelService.existsByTitle(dto.getTitle())) {
            throw new UnexpectedException("No such label");
        }

        Label label = Label.parseListDto(dto);
        Note note = read(id);
        note.addRelatedLabel(label);

        if (!labelService.isNotePresentInLabelRelatedNotes(note, label)) {
            labelService.addRelatedNote(label.getId(), ListDto.parseObject(note));
        }
        return note;
    }
}
