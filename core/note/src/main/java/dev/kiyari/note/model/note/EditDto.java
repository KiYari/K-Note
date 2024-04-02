package dev.kiyari.note.model.note;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditDto {
    private String title;
    private String description;
    private String note;
    private Set<Note> relatedNotes;
    private Set<Label> relatedLabels;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;


    public static EditDto parseObject(Note note) {
        if(note == null) {
            return new EditDto();
        }
        return EditDto.builder()
                .title(note.getTitle())
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
        return "EditDto{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditDto editDto)) return false;
        return Objects.equals(title, editDto.title) && Objects.equals(description, editDto.description) && Objects.equals(note, editDto.note) && Objects.equals(relatedNotes, editDto.relatedNotes) && Objects.equals(relatedLabels, editDto.relatedLabels) && Objects.equals(dateCreated, editDto.dateCreated) && Objects.equals(lastUpdated, editDto.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, note, dateCreated, lastUpdated);
    }
}
