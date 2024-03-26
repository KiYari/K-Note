package dev.kiyari.note.model.entity;

import dev.kiyari.note.model.BasicEntity;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.model.note.ListDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "note")
@NoArgsConstructor
@Getter
public class Note extends BasicEntity {

    @Builder
    public Note(Long id, @NonNull String label, String description, @NonNull String note, Set<Note> relatedNotes, Set<Label> relatedLabels,
                LocalDateTime dateCreated, LocalDateTime lastUpdated) {
        super(id);
        this.label = label;
        this.description = description;
        this.note = note;
        this.relatedNotes = relatedNotes;
        this.relatedLabels = relatedLabels;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    @Column(name = "label")
    @NonNull
    private String label;
    @Column(name = "description")
    private String description;
    @Column(name = "note")
    @NonNull
    private String note;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "note_note",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "related_note_id"))
    private Set<Note> relatedNotes;
    @ManyToMany(mappedBy = "relatedLabels",
            cascade = CascadeType.PERSIST)
    private Set<Label> relatedLabels = new HashSet<>();


    public static Note parseEditDto(EditDto dto) {
        if (dto == null) {
            return new Note();
        }
        return Note.builder()
                .note(dto.getNote())
                .label(dto.getLabel())
                .description(dto.getDescription())
                .relatedNotes(dto.getRelatedNotes())
                .relatedLabels(dto.getRelatedLabels())
                .dateCreated(dto.getDateCreated())
                .lastUpdated(dto.getLastUpdated())
                .build();
    }

    public static Note parseDto(ListDto dto) {
        if (dto == null) {
            return new Note();
        }
        return Note.builder()
                .id(dto.getId())
                .note(dto.getNote())
                .label(dto.getLabel())
                .description(dto.getDescription())
                .relatedNotes(dto.getRelatedNotes())
                .relatedLabels(dto.getRelatedLabels())
                .dateCreated(dto.getDateCreated())
                .lastUpdated(dto.getLastUpdated())
                .build();
    }

    @Override
    public String toString() {
        return "Note{" +
                "label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note note1)) return false;
        return label.equals(note1.label) && Objects.equals(description, note1.description) && note.equals(note1.note) && Objects.equals(dateCreated, note1.dateCreated) && Objects.equals(lastUpdated, note1.lastUpdated) && Objects.equals(relatedNotes, note1.relatedNotes) && Objects.equals(relatedLabels, note1.relatedLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, description, note, dateCreated, lastUpdated, relatedLabels);
    }
}
