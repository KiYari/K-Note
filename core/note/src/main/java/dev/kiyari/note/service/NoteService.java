package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.note.ListDto;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.repository.NoteRepository;
import dev.kiyari.note.util.exception.CreateEntityException;
import dev.kiyari.note.util.exception.UnexpectedException;
import dev.kiyari.note.util.exception.DeleteEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

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
            if (noteRepository.existsByTitle(note.getTitle())) {
                throw new CreateEntityException("Note with such title already exists");
            }
            return noteRepository.save(Note.parseEditDto(note));
        }
        throw new UnexpectedException("Could not save Note due to unexpected reasons.");
    }

    public Note update(EditDto note) {
        return save(note);
    }

    public Note delete(Long id) {
        if(noteRepository.existsById(id)) {
            Note note = read(id);
            noteRepository.delete(note);
            return note;
        }
        throw new DeleteEntityException("Could not delete note due to unexpected reasons.");
    }
}
