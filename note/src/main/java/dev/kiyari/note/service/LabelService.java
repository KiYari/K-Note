package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.label.EditDto;
import dev.kiyari.note.repository.LabelRepository;
import dev.kiyari.note.util.exception.CreateEntityException;
import dev.kiyari.note.util.exception.DeleteEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Label save(EditDto note) {
        if (note != null) {
            return labelRepository.save(Label.parseEditDto(note));
        }
        throw new CreateEntityException("Could not save Label due to unexpected reasons.");
    }

    public Label update(EditDto note) {
        return save(note);
    }

    public Label delete(Long id) {
        if(labelRepository.existsById(id)) {
            Label label = read(id);

            labelRepository.delete(label);

            return label;
        }
        throw new DeleteEntityException("Could not delete Label due to unexpected reasons.");
    }
}
