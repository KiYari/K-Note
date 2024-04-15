package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.model.note.ListDto;
import dev.kiyari.note.repository.NoteRepository;
import dev.kiyari.note.util.exception.EntityAlreadyPresentException;
import dev.kiyari.note.util.exception.NoSuchEntityException;
import dev.kiyari.note.util.exception.SaveEntityException;
import dev.kiyari.note.util.exception.UnexpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
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

    @Transactional
    public Note save(EditDto note) {
        if (note != null) {
            note.setDateCreated(LocalDateTime.now());
            note.setLastUpdated(LocalDateTime.now());
            return noteRepository.save(Note.parseEditDto(note));
        }
        throw new UnexpectedException("Could not save Note due to unexpected reasons.");
    }

    @Transactional
    public Note update(Long id, EditDto dto) {
        if (!existsById(id)) {
            throw new SaveEntityException("There is no entity with such ID to update");
        }
        Note existing = read(id);

        existing.setNote(dto.getNote());
        existing.setLastUpdated(LocalDateTime.now());
        existing.setDescription(dto.getDescription());
        existing.setTitle(dto.getTitle());
        existing.setRelatedNotes(dto.getRelatedNotes());
        existing.setRelatedLabels(dto.getRelatedLabels());

        return noteRepository.save(existing);
    }

    public Note update(Note note) {
        return update(note.getId(), EditDto.parseObject(note));
    }

    public Note delete(Long id) {
        if(existsById(id)) {
            Note note = read(id);
            noteRepository.delete(note);
            return note;
        }
        throw new EntityAlreadyPresentException("Could not delete note due to unexpected reasons.");
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

    public Boolean isNotePresentsInNoteRelatedNotes(Note relatedNote, Note note) {
        return note.getRelatedNotes().contains(relatedNote);
    }

    public Note addRelatedLabel(Long id, Long labelId) {
        if (!labelService.existsById(labelId)) {
            throw new UnexpectedException("No such label");
        }

        Label label = labelService.read(labelId);
        Note note = read(id);
        if (isLabelPresentsInNoteRelatedLabels(label, note)) {
            throw new EntityAlreadyPresentException("Such label is already present in relatedNotes");
        }

        note.addRelatedLabel(label);

        labelService.addRelatedNote(label.getId(), ListDto.parseObject(note));

        return update(note);
    }

    public Note addRelatedNote(Long id, Long noteId) {
        if (!existsById(noteId)) {
            throw new UnexpectedException("No such note");
        }

        Note noteToAdd = read(noteId);
        Note note = read(id);
        if (isNotePresentsInNoteRelatedNotes(noteToAdd, note)) {
            throw new EntityAlreadyPresentException("Such label is already present in relatedNotes");
        }

        note.addRelatedNote(noteToAdd);
        noteToAdd.addRelatedNote(note);

        update(noteToAdd);
        return update(note);
    }

    public Note removeRelatedLabel(Long id, Long label_id) {
        if (!labelService.existsById(label_id)) {
            throw new UnexpectedException("No such label");
        }

        Label label = labelService.read(label_id);
        Note note = read(id);

        if (!isLabelPresentsInNoteRelatedLabels(label, note)) {
            throw new NoSuchEntityException("There is no such Label in relatedLabels");
        }

        labelService.removeRelatedNote(label.getId(), ListDto.parseObject(note));
        note.removeRelatedLabel(label);
        update(note);

        return note;
    }

    public Note removeRelatedNote(Long id, Long noteId) {
        if (!existsById(noteId)) {
            throw new UnexpectedException("No such label");
        }

        Note noteToAdd = read(noteId);
        Note note = read(id);

        if (!isNotePresentsInNoteRelatedNotes(noteToAdd, note)) {
            throw new NoSuchEntityException("There is no such Label in relatedLabels");
        }

        note.removeRelatedNote(noteToAdd);
        noteToAdd.removeRelatedNote(note);

        update(noteToAdd);

        return update(note);
    }

    public Set<Label> getRelatedLabels(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return note.get().getRelatedLabels();
        }

        throw new NoSuchEntityException("There is no note with such id");
    }

    public Set<Note> getRelatedNotes(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return note.get().getRelatedNotes();
        }

        throw new NoSuchEntityException("There is no note with such id");
    }
}
