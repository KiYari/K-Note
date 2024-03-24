package dev.kiyari.note.model.entity;

import dev.kiyari.note.model.BasicEntity;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.model.note.ListDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "note")
@NoArgsConstructor
@Getter
public class Note extends BasicEntity {
    @Builder
    public Note(Long id, @NonNull String label, String description, @NonNull String note, Set<Note> relatedNotes) {
        super(id);
        this.label = label;
        this.description = description;
        this.note = note;
        this.relatedNotes = relatedNotes;
    }

    @Column(name = "label")
    @NonNull
    private String label;
    @Column(name = "description")
    private String description;
    @Column(name = "note")
    @NonNull
    private String note;
    @ManyToMany
    private Set<Note> relatedNotes;

    public static Note parseEditDto(EditDto dto) {
        return Note.builder()
                .note(dto.getNote())
                .label(dto.getLabel())
                .description(dto.getDescription())
                .relatedNotes(dto.getRelatedNotes())
                .build();
    }

    public static Note parseDto(ListDto dto) {
        return Note.builder()
                .id(dto.getId())
                .note(dto.getNote())
                .label(dto.getLabel())
                .description(dto.getDescription())
                .relatedNotes(dto.getRelatedNotes())
                .build();
    }
}
