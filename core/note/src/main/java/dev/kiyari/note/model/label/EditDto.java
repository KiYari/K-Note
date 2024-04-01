package dev.kiyari.note.model.label;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditDto {
    private Long id;
    private String title;
    private String description;
    private Set<Note> relatedNotes;
    private Set<Label> relatedLabels;

    public static EditDto parseObject(Label label) {
        if(label == null) {
            return new EditDto();
        }
        return EditDto.builder()
                .id(label.getId())
                .title(label.getTitle())
                .description(label.getDescription())
                .relatedNotes(label.getRelatedNotes())
                .relatedLabels(label.getRelatedLabels())
                .build();
    }

    @Override
    public String toString() {
        return "EditDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditDto editDto)) return false;
        return Objects.equals(title, editDto.title) && Objects.equals(description, editDto.description) && Objects.equals(relatedNotes, editDto.relatedNotes) && Objects.equals(relatedLabels, editDto.relatedLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}
