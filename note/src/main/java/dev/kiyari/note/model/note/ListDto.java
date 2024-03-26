package dev.kiyari.note.model.note;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDto {
    private Long id;
    private String label;
    private String description;
    private String note;
    private Set<Note> relatedNotes;
    private Set<Label> relatedLabels;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;


    public static ListDto parseObject(Note note) {
        if (note == null) {
            return new ListDto();
        }

        return ListDto.builder()
                .id(note.getId())
                .label(note.getLabel())
                .note(note.getNote())
                .description(note.getDescription())
                .relatedNotes(note.getRelatedNotes())
                .relatedLabels(note.getRelatedLabels())
                .dateCreated(note.getDateCreated())
                .lastUpdated(note.getLastUpdated())
                .build();
    }

    @Override
    public String toString() {
        return "ListDto{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListDto listDto)) return false;
        return Objects.equals(id, listDto.id) && Objects.equals(label, listDto.label) && Objects.equals(description, listDto.description) && Objects.equals(note, listDto.note) && Objects.equals(relatedNotes, listDto.relatedNotes) && Objects.equals(relatedLabels, listDto.relatedLabels) && Objects.equals(dateCreated, listDto.dateCreated) && Objects.equals(lastUpdated, listDto.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, description, note, dateCreated, lastUpdated);
    }
}
