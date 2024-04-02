package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.repository.NoteRepository;
import dev.kiyari.note.util.exception.DeleteEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTests {

    @Mock
    protected NoteRepository noteRepository;

    @InjectMocks
    protected NoteService noteService;
    private List<Note> notes = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        notes.add(new Note(1L, "Important Note", "Meeting details with John Doe",
                "Call John Doe at (555) 555-1234 to discuss project progress.",
                new HashSet<>(), new HashSet<>(),
                LocalDateTime.now(), LocalDateTime.now()));

        notes.add(new Note(2L, "Grocery List", "Items to buy for the week",
                "Milk, Bread, Eggs, Cheese, Apples, Bananas",
                new HashSet<>(), new HashSet<>(),
                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2)));

        notes.add(new Note(3L, "Birthday Ideas", "Gift ideas for Sarah's birthday",
                "New book, Tickets to a concert, Spa day",
                new HashSet<>(), new HashSet<>(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1)));

        labels.add(new Label(1L, "Captivating Cosmos", "Explore the wonders of the universe",
                new HashSet<>(), new HashSet<>()));
    }

    @Test
    public void testReadNote_ExistingId() {
        Long existingId = 1L;
        when(noteRepository.findById(existingId)).thenReturn(Optional.of(notes.get(0)));

        Note actualNote = noteService.read(existingId);

        assertNotNull(actualNote);
        assertEquals(notes.get(0).getId(), actualNote.getId());
        assertEquals(notes.get(0).getTitle(), actualNote.getTitle());
        assertEquals(notes.get(0).getDescription(), actualNote.getDescription()); // Assert description
        assertEquals(notes.get(0).getNote(), actualNote.getNote());
        assertEquals(notes.get(0).getRelatedNotes(), actualNote.getRelatedNotes());
        assertEquals(notes.get(0).getRelatedLabels(), actualNote.getRelatedLabels());
        assertEquals(notes.get(0).getDateCreated(), actualNote.getDateCreated());
        assertEquals(notes.get(0).getLastUpdated(), actualNote.getLastUpdated());
    }

    @Test
    public void testReadNote_NonExistingId() {
        Long nonExistingId = 4L;
        when(noteRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> noteService.read(nonExistingId));
    }

    @Test
    public void testSaveNote_ValidData() {
        Note newNote = new Note(null, "New Shopping List", "Items needed for the weekend",
                "Bread, Eggs, Milk, Juice", new HashSet<>(), new HashSet<>(),
                LocalDateTime.now(), LocalDateTime.now());

        when(noteRepository.save(newNote)).thenReturn(newNote);

        Note savedNote = noteService.save(EditDto.parseObject(newNote));

        assertNotNull(savedNote);
        assertEquals(newNote.getTitle(), savedNote.getTitle());
        assertEquals(newNote.getDescription(), savedNote.getDescription());
        assertEquals(newNote.getNote(), savedNote.getNote());
        assertEquals(newNote.getRelatedNotes(), savedNote.getRelatedNotes());
        assertEquals(newNote.getRelatedLabels(), savedNote.getRelatedLabels());
        assertEquals(newNote.getDateCreated(), savedNote.getDateCreated());
        assertEquals(newNote.getLastUpdated(), savedNote.getLastUpdated());

        verify(noteRepository).save(newNote);
    }

    @Test
    public void testUpdateNote_ValidData() {
        Long noteToUpdateId = 2L;
        Note updatedNote = new Note(noteToUpdateId, "Updated Grocery List", "Revised items",
                "Milk, Eggs, Cheese, Apples", new HashSet<>(), new HashSet<>(),
                LocalDateTime.now(), LocalDateTime.now());

        when(noteRepository.save(updatedNote)).thenReturn(updatedNote);

        Note savedNote = noteService.update(noteToUpdateId, EditDto.parseObject(updatedNote));

        assertNotNull(savedNote);
        assertEquals(updatedNote.getId(), savedNote.getId());
        assertEquals(updatedNote.getTitle(), savedNote.getTitle());
        assertEquals(updatedNote.getDescription(), savedNote.getDescription());
        assertEquals(updatedNote.getNote(), savedNote.getNote());
        assertEquals(updatedNote.getRelatedNotes(), savedNote.getRelatedNotes());
        assertEquals(updatedNote.getRelatedLabels(), savedNote.getRelatedLabels());
        assertEquals(updatedNote.getDateCreated(), savedNote.getDateCreated());
        assertEquals(updatedNote.getLastUpdated(), savedNote.getLastUpdated());

        verify(noteRepository).save(updatedNote);
    }

    @Test
    public void testDeleteNote_ExistingId() {
        Long noteToDeleteId = 1L;
        when(noteRepository.findById(noteToDeleteId)).thenReturn(Optional.of(notes.get(0)));

        when(noteRepository.existsById(1L)).thenReturn(true);
        noteService.delete(noteToDeleteId);

        verify(noteRepository).delete(notes.get(0));
    }

    @Test
    public void testDeleteNote_NonExistingId() {
        Long nonExistingId = 4L;
        when(noteRepository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(DeleteEntityException.class, () -> noteService.delete(nonExistingId));
    }

    @Test
    public void testExistsByIdNullReturnsFalse() {

        assertFalse(noteRepository.existsById(null));
    }

    @Test
    public void testExistsByIdIncorrectReturnsFalse() {
        assertFalse(noteService.existsById(-1L));
    }

    @Test
    public void testIsNotePresentInRelatedNotes() {
        Label label = labels.get(0);
        Note note = new Note();

        note.addRelatedLabel(label);

        assertTrue(noteService.isLabelPresentsInNoteRelatedLabels(label, note));
    }
}
