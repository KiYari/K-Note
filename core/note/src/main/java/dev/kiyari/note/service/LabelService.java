package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.label.EditDto;
import dev.kiyari.note.repository.LabelRepository;
import dev.kiyari.note.util.exception.EntityAlreadyPresentException;
import dev.kiyari.note.util.exception.NoSuchEntityException;
import dev.kiyari.note.util.exception.SaveEntityException;
import dev.kiyari.note.util.exception.UnexpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;

    public Label read(Long id) {
        return labelRepository.findById(id).orElseThrow();
    }

    public Set<Label> getAll() {
        Set<Label> notes = new LinkedHashSet<>();

        labelRepository.findAll().forEach(notes::add);

        return notes;
    }

    public Label save(EditDto dto) {
        if (dto != null) {
            if (existsByTitle(dto.getTitle())) {
                throw new SaveEntityException("Label with this title already exists");
            }
            return labelRepository.save(Label.parseEditDto(dto));
        }
        throw new UnexpectedException("Could not save Label due to unexpected reasons.");
    }

    public Label update(Long id, EditDto label) {
        if (!existsById(id)) {
            throw new SaveEntityException("There is no entity with such ID to update");
        }
        Label existing = read(id);

        existing.setDescription(label.getDescription());
        existing.setTitle(label.getTitle());
        existing.setRelatedLabels(label.getRelatedLabels());
        existing.setRelatedNotes(label.getRelatedNotes());

        return labelRepository.save(existing);
    }

    public Label update(Label label) {
        return update(label.getId(), EditDto.parseObject(label));
    }

    public Label delete(Long id) {
        if(existsById(id)) {
            Label label = read(id);

            labelRepository.delete(label);

            return label;
        }
        throw new EntityAlreadyPresentException("Could not delete Label due to unexpected reasons.");
    }

    public Boolean existsById(Long id) {
        if (id == null || id < 0) {
            return false;
        }
        return labelRepository.existsById(id);
    }

    public Boolean existsByTitle(String title) {
        if (title == null || title.trim().equals("")) {
            return false;
        }
        return labelRepository.existsByTitle(title);
    }

    public Boolean isNotePresentInLabelRelatedNotes(Note note, Label label) {
        return label.getRelatedNotes().contains(note);
    }

    public Boolean addRelatedNote(Long id, dev.kiyari.note.model.note.ListDto dto) {
        Label label = read(id);
        Note note = Note.parseDto(dto);

        if (!isNotePresentInLabelRelatedNotes(note, label)) {
            label.addRelatedNote(note);
            update(label);

            return true;
        }

        return false;
    }

    public Boolean removeRelatedNote(Long id, dev.kiyari.note.model.note.ListDto dto) {
        Label label = read(id);
        Note note = Note.parseDto(dto);

        if (!isNotePresentInLabelRelatedNotes(note, label)) {
            throw new NoSuchEntityException("There is no such Note in RelatedNotes");
        }

        label.removeRelatedNote(note);

        update(label);
        return true;
    }
}
