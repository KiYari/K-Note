package dev.kiyari.note.model.note;

import dev.kiyari.note.model.entity.Note;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class EditDto {
    private String label;
    private String description;
    private String note;
    private Set<Note> relatedNotes;


    public static EditDto parseObject(Note note) {
        return EditDto.builder()
                .label(note.getLabel())
                .note(note.getNote())
                .description(note.getDescription())
                .relatedNotes(note.getRelatedNotes())
                .build();
    }
}
