package dev.kiyari.note.model.note;

import dev.kiyari.note.model.entity.Note;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class ListDto {
    private Long id;
    private String label;
    private String description;
    private String note;
    private Set<Note> relatedNotes;


    public static ListDto parseObject(Note note) {
        return ListDto.builder()
                .id(note.getId())
                .label(note.getLabel())
                .note(note.getNote())
                .description(note.getDescription())
                .relatedNotes(note.getRelatedNotes())
                .build();
    }
}
