package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.label.ListDto;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.repository.LabelRepository;
import dev.kiyari.note.repository.NoteRepository;
import dev.kiyari.note.util.exception.EntityAlreadyPresentException;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTests {

    @Mock
    protected NoteRepository noteRepository;

    @InjectMocks
    protected NoteService noteService;
    @Mock
    protected LabelService labelService;
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

        labels.add(new Label(2L, "Flourishing Flora", "Discover the diversity of plant life",
                new HashSet<>(), new HashSet<>()));

        labels.add(new Label(3L, "Mesmerizing Music", "Delve into the world of sound",
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
        when(noteRepository.existsById(noteToUpdateId)).thenReturn(true);
        when(noteRepository.findById(noteToUpdateId)).thenReturn(Optional.ofNullable(updatedNote));

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

        assertThrows(EntityAlreadyPresentException.class, () -> noteService.delete(nonExistingId));
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
    public void testIsLabelPresentInRelatedLabels() {
        Label label = labels.get(0);
        Note note = new Note();

        note.addRelatedLabel(label);

        assertTrue(noteService.isLabelPresentsInNoteRelatedLabels(label, note));
    }

    @Test
    public void testIsNotePresentInRelatedNotes() {
        Note relatedNote = notes.get(1);
        Note note = notes.get(0);

        note.addRelatedNote(relatedNote);

        assertTrue(noteService.isNotePresentsInNoteRelatedNotes(relatedNote, note));
    }

    @Test
    public void testRemoveRelatedLabel_ShouldReturnNote() {
        Long id = 1L;
        Long labelId = 1L;
        Label label = labels.get(0);
        Note note = notes.get(0);
        Note unchangedNote = note.clone();
        note.addRelatedLabel(label);
        label.addRelatedNote(note);

        when(labelService.existsById(id)).thenReturn(true);
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(noteRepository.existsById(id)).thenReturn(true);
        when(labelService.read(labelId)).thenReturn(label);

        noteService.removeRelatedLabel(id, labelId);

        assertEquals(note.getId(), unchangedNote.getId());
        assertEquals(note.getTitle(), unchangedNote.getTitle());
        assertEquals(note.getDescription(), unchangedNote.getDescription());
        assertEquals(note.getNote(), unchangedNote.getNote());
        assertEquals(note.getDateCreated(), unchangedNote.getDateCreated());
        assertEquals(note.getRelatedLabels(), unchangedNote.getRelatedLabels());

        assertFalse(note.getRelatedLabels().contains(label));
    }

    @Test
    public void testRemoveRelatedLabel_ShouldDifferFromUnchanged() {
        Long id = 1L;
        Label label = labels.get(0);
        Note note = notes.get(0);
        Note unchangedNote = note.clone();
        note.addRelatedLabel(label);
        label.addRelatedNote(note);

        assertEquals(note.getId(), unchangedNote.getId());
        assertEquals(note.getTitle(), unchangedNote.getTitle());
        assertEquals(note.getDescription(), unchangedNote.getDescription());
        assertEquals(note.getNote(), unchangedNote.getNote());
        assertEquals(note.getDateCreated(), unchangedNote.getDateCreated());
        assertNotEquals(note.getRelatedLabels(), unchangedNote.getRelatedLabels());
    }

    @Test
    public void testRemoveRelatedNote_ShouldReturnNote() {
        Long id = 1L;
        Long note2Id = 2L;
        Note noteToAdd = notes.get(1);
        Note note = notes.get(0);
        Note unchangedNote = note.clone();
        note.addRelatedNote(noteToAdd);
        noteToAdd.addRelatedNote(note);

        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(noteRepository.existsById(id)).thenReturn(true);
        when(noteRepository.findById(note2Id)).thenReturn(Optional.of(noteToAdd));
        when(noteRepository.existsById(note2Id)).thenReturn(true);

        noteService.removeRelatedNote(id, note2Id);

        assertEquals(note.getId(), unchangedNote.getId());
        assertEquals(note.getTitle(), unchangedNote.getTitle());
        assertEquals(note.getDescription(), unchangedNote.getDescription());
        assertEquals(note.getNote(), unchangedNote.getNote());
        assertEquals(note.getDateCreated(), unchangedNote.getDateCreated());
        assertEquals(note.getRelatedLabels(), unchangedNote.getRelatedLabels());

        assertFalse(note.getRelatedLabels().contains(noteToAdd));
    }

    @Test
    public void testRemoveRelatedNote_ShouldDifferFromUnchanged() {
        Note NoteToRemove = notes.get(1);
        Note note = notes.get(0);
        Note unchangedNote = note.clone();
        note.addRelatedNote(NoteToRemove);
        NoteToRemove.addRelatedNote(note);

        assertEquals(note.getId(), unchangedNote.getId());
        assertEquals(note.getTitle(), unchangedNote.getTitle());
        assertEquals(note.getDescription(), unchangedNote.getDescription());
        assertEquals(note.getNote(), unchangedNote.getNote());
        assertEquals(note.getDateCreated(), unchangedNote.getDateCreated());
        assertNotEquals(note.getRelatedNotes(), unchangedNote.getRelatedNotes());
    }

    @Test
    public void testGetRelatedLabels_ShouldReturnValidRelatedLabels() {
        Long id = 1L;
        Label label = labels.get(0);
        Note note = notes.get(0);
        note.addRelatedLabel(label);

        when(noteRepository.findById(id)).thenReturn(Optional.of(note));

        assertEquals(note.getRelatedLabels(), noteService.getRelatedLabels(id));
    }

    @Test
    public void testGetRelatedNotes_ShouldReturnValidRelatedNotes() {
        Long id = 1L;
        Note note = notes.get(0);
        Note note2 = notes.get(1);
        note.addRelatedNote(note2);

        when(noteRepository.findById(id)).thenReturn(Optional.of(note));

        assertEquals(note.getRelatedLabels(), noteService.getRelatedLabels(id));
    }

    @Test
    public void testAddRelatedNotes_ShouldReturnValidNote() {
        Long id = 1L;
        Long addId = 2L;
        Note note = notes.get(0);
        Note noteToAdd = notes.get(1);

        when(noteRepository.existsById(id)).thenReturn(true);
        when(noteRepository.existsById(addId)).thenReturn(true);
        when(noteRepository.findById(id)).thenReturn(Optional.ofNullable(note));
        when(noteRepository.findById(addId)).thenReturn(Optional.ofNullable(noteToAdd));

        noteService.addRelatedNote(id, addId);

        assert note != null;
        System.out.println(note.getRelatedNotes());
        assertTrue(note.getRelatedNotes()
                .stream().toList()
                .get(0).equalsIgnoreLastUpdate(noteToAdd));
        assert noteToAdd != null;
        assertTrue(noteToAdd.getRelatedNotes()
                .stream().toList()
                .get(0).equalsIgnoreLastUpdate(note));
    }
}
