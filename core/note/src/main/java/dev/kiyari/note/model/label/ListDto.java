package dev.kiyari.note.model.label;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListDto {
    private Long id;
    private String title;
    private String description;
    private Set<Note> relatedNotes;
    private Set<Label> relatedLabels;


    public static ListDto parseObject(Label label) {
        if (label == null) {
            return new ListDto();
        }

        return ListDto.builder()
                .id(label.getId())
                .title(label.getTitle())
                .description(label.getDescription())
                .relatedNotes(label.getRelatedNotes())
                .relatedLabels(label.getRelatedLabels())
                .build();
    }

    @Override
    public String toString() {
        return "ListDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListDto listDto)) return false;
        return Objects.equals(id, listDto.id) && Objects.equals(title, listDto.title) && Objects.equals(description, listDto.description) && Objects.equals(relatedNotes, listDto.relatedNotes) && Objects.equals(relatedLabels, listDto.relatedLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
