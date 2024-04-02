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
    public Note(Long id, @NonNull String title, String description, @NonNull String note, Set<Note> relatedNotes, Set<Label> relatedLabels,
                LocalDateTime dateCreated, LocalDateTime lastUpdated) {
        super(id);
        this.title = title;
        this.description = description;
        this.note = note;
        this.relatedNotes = relatedNotes;
        this.relatedLabels = relatedLabels;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    @Column(name = "title")
    @NonNull
    @Setter
    private String title;
    @Column(name = "description")
    @Setter
    private String description;
    @Column(name = "note")
    @Setter
    private String note;
    private LocalDateTime dateCreated;
    @Setter
    private LocalDateTime lastUpdated;
    @ManyToMany(cascade = CascadeType.PERSIST,
                fetch = FetchType.EAGER)
    @JoinTable(name = "note_note",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "related_note_id"))
    private Set<Note> relatedNotes;
    @ManyToMany(mappedBy = "relatedNotes",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private Set<Label> relatedLabels = new HashSet<>();

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

    public static Note parseEditDto(EditDto dto) {
        if (dto == null) {
            return new Note();
        }
        return Note.builder()
                .note(dto.getNote())
                .title(dto.getTitle())
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
                .title(dto.getTitle())
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
                "title='" + title + '\'' +
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
        return title.equals(note1.title) && Objects.equals(description, note1.description) && note.equals(note1.note) && Objects.equals(dateCreated, note1.dateCreated) && Objects.equals(lastUpdated, note1.lastUpdated) && Objects.equals(relatedNotes, note1.relatedNotes) && Objects.equals(relatedLabels, note1.relatedLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, note, dateCreated, lastUpdated, relatedLabels);
    }
}
