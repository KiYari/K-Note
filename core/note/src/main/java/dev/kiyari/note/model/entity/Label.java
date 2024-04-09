package dev.kiyari.note.model.entity;

import dev.kiyari.note.model.BasicEntity;
import dev.kiyari.note.model.label.EditDto;
import dev.kiyari.note.model.label.ListDto;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "label")
@NoArgsConstructor
@Getter
public class Label extends BasicEntity implements Cloneable{

    @Builder
    public Label(Long id, String title, String description, Set<Note> relatedNotes, Set<Label> relatedLabels) {
        super(id);
        this.title = title;
        this.description = description;
        this.relatedNotes = relatedNotes;
        this.relatedLabels = relatedLabels;
    }

    @NonNull
    @Setter
    private String title;
    @Setter
    private String description;

    @ManyToMany(mappedBy = "relatedLabels",
            fetch = FetchType.LAZY)
    @Setter
    private Set<Note> relatedNotes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "label_label",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name = "related_label_id"))
    @Setter
    private Set<Label> relatedLabels = new HashSet<>();

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

    public void addRelatedLabel(Label label) {
        relatedLabels.add(label);
    }

    public void removeRelatedLabel(Label label) {
        relatedLabels.remove(label);
    }

    public void addRelatedNote(Note note) {
        relatedNotes.add(note);
    }

    public void removeRelatedNote(Note note) {
        relatedNotes.remove(note);
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

    @Override
    public Label clone() {
        try {
            Label clone = (Label) super.clone();

            Set<Note> clonedRelatedNotes = new HashSet<>();
            for (Note relatedNote : relatedNotes) {
                clonedRelatedNotes.add(relatedNote.clone());
            }
            clone.relatedNotes = clonedRelatedNotes;

            Set<Label> clonedRelatedLabels = new HashSet<>();
            for (Label relatedLabel : relatedLabels) {
                clonedRelatedLabels.add(relatedLabel.clone());
            }
            clone.relatedLabels = clonedRelatedLabels;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
