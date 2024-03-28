package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.repository.NoteRepository;
import dev.kiyari.note.util.exception.CreateEntityException;
import dev.kiyari.note.util.exception.DeleteEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
            return noteRepository.save(Note.parseEditDto(note));
        }
        throw new CreateEntityException("Could not save Note due to unexpected reasons.");
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
