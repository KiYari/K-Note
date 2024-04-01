package dev.kiyari.note.model.entity;

import dev.kiyari.note.model.BasicEntity;
import dev.kiyari.note.model.label.EditDto;
import dev.kiyari.note.model.label.ListDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "label")
@NoArgsConstructor
@Getter
public class Label extends BasicEntity {

    @Builder
    public Label(Long id, String title, String description, Set<Note> relatedNotes, Set<Label> relatedLabels) {
        super(id);
        this.title = title;
        this.description = description;
        this.relatedNotes = relatedNotes;
        this.relatedLabels = relatedLabels;
    }

    @NonNull
    private String title;
    private String description;

    @ManyToMany
    @JoinTable(name = "note_label",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id"))
    private Set<Note> relatedNotes;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "label_label",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name = "related_label_id"))
    private Set<Label> relatedLabels;

    public static Label parseEditDto(EditDto dto) {
        if (dto == null) {
            return new Label();
        }
        return Label.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .relatedNotes(dto.getRelatedNotes())
                .relatedLabels(dto.getRelatedLabels())
                .build();
    }

    public static Label parseListDto(ListDto dto) {
        if (dto == null) {
            return new Label();
        }
        return Label.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .relatedNotes(dto.getRelatedNotes())
                .relatedLabels(dto.getRelatedLabels())
                .build();
    }

    public void addRelatedNote(Note note) {
        relatedNotes.add(note);
    }

    @Override
    public String toString() {
        return "Label{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label label)) return false;
        return title.equals(label.title) && Objects.equals(description, label.description) && Objects.equals(relatedNotes, label.relatedNotes) && Objects.equals(relatedLabels, label.relatedLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}
