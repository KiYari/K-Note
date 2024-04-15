package dev.kiyari.note.controller;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.note.EditDto;
import dev.kiyari.note.model.note.ListDto;
import dev.kiyari.note.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteControllerTests {
    @InjectMocks
    private NoteController noteController;

    @Mock
    private NoteService noteService;

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

    private void assertNoteFields(ListDto dto) {
        assertNotNull(dto.getId());
        assertNotNull(dto.getTitle());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getNote());
        assertNotNull(dto.getDateCreated());
        assertNotNull(dto.getLastUpdated());
    }

    private void assertNoteFields(EditDto dto) {
        assertNotNull(dto.getTitle());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getNote());
        assertNotNull(dto.getDateCreated());
        assertNotNull(dto.getLastUpdated());
    }

    @Test
    public void getAllTest() {
        Mockito.when(noteService.getAll()).thenReturn(new HashSet<>(notes));

        ResponseEntity<Set<ListDto>> response = noteController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Set<ListDto> dtos = response.getBody();
        assert dtos != null;
        assertEquals(3, dtos.size());
        assertNoteFields(dtos.iterator().next());
    }

    @Test
    public void readTest() {
        Long id = 1L;
        Note note = notes.get(0);
        Mockito.when(noteService.read(id)).thenReturn(note);

        ResponseEntity<ListDto> response = noteController.read(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ListDto dto = response.getBody();
        assert dto != null;
        assertNoteFields(dto);
    }

    @Test
    public void createTest() {
        EditDto dto = EditDto.parseObject(notes.get(0));
        Note savedNote = notes.get(0); // Assuming noteService.save() returns an existing note
        Mockito.when(noteService.save(dto)).thenReturn(savedNote);

        ResponseEntity<ListDto> response = noteController.create(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ListDto responseDto = response.getBody();
        assert responseDto != null;
        assertNoteFields(responseDto);
    }

    @Test
    public void updateTest() {
        Long id = 2L;
        EditDto dto = EditDto.builder() // ... (set values for EditDto)
                .build();
        Note updatedNote = notes.get(1); // Assuming noteService.update() returns the updated note
        Mockito.when(noteService.update(id, dto)).thenReturn(updatedNote);

        ResponseEntity<ListDto> response = noteController.update(id, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ListDto responseDto = response.getBody();
        assert responseDto != null;
        assertNoteFields(responseDto);
    }

    @Test
    public void deleteTest() {
        Long id = 3L;
        Note deletedNote = notes.get(2);
        Mockito.when(noteService.delete(id)).thenReturn(deletedNote);

        ResponseEntity<ListDto> response = noteController.delete(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ListDto dto = response.getBody();
        assert dto != null;
        assertNoteFields(dto);
    }

    @Test
    public void addRelatedLabelTest() {
        Long id = 1L;
        Long labelId = 1L;
        Label label = labels.get(0);
        Note note = notes.get(0);
        note.addRelatedLabel(label);

        when(noteService.addRelatedLabel(id, labelId)).thenReturn(note);

        ResponseEntity<ListDto> response = noteController.addRelatedLabel(id,labelId);

        ListDto dto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert dto != null;
        assertNoteFields(dto);
    }

    @Test
    public void removeRelatedLabelTest() {
        Long id = 1L;
        Long labelId = 1L;
        Label label = labels.get(0);
        Note note = notes.get(0);

        when(noteService.removeRelatedLabel(id, labelId)).thenReturn(note);

        ResponseEntity<ListDto> response = noteController.deleteRelatedLabel(id, labelId);

        ListDto dto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert dto != null;
        assertNoteFields(dto);
    }

    @Test
    public void removeRelatedNoteTest() {
        Long id = 1L;
        Long noteRemoveId = 2L;
        Note noteToRemove = notes.get(1);
        Note note = notes.get(0);

        when(noteService.removeRelatedNote(id, noteRemoveId)).thenReturn(note);

        ResponseEntity<ListDto> response = noteController.deleteRelatedNote(id, noteRemoveId);

        ListDto dto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert dto != null;
        assertNoteFields(dto);
    }

    @Test
    public void getRelatedLabelsTest() {
        Long id = 1L;
        Label label = labels.get(0);
        Note note = notes.get(0);
        note.addRelatedLabel(label);

        Set<dev.kiyari.note.model.label.ListDto> set = new HashSet<>();
        set.add(dev.kiyari.note.model.label.ListDto.parseObject(label));

        when(noteService.getRelatedLabels(id)).thenReturn(note.getRelatedLabels());

        ResponseEntity<Set<dev.kiyari.note.model.label.ListDto>> response = noteController.getRelatedLabels(id);
        Set<dev.kiyari.note.model.label.ListDto> relatedLabels = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(relatedLabels);
        assertEquals(set, relatedLabels);
    }

    @Test
    public void getRelatedNoteTest() {
        Long id = 1L;
        Note note = notes.get(0);
        Note note2 = notes.get(1);
        note.addRelatedNote(note2);

        Set<ListDto> set = new HashSet<>();
        set.add(ListDto.parseObject(note2));

        when(noteService.getRelatedNotes(id)).thenReturn(note.getRelatedNotes());

        ResponseEntity<Set<ListDto>> response = noteController.getRelatedNotes(id);
        Set<ListDto> relatedLabels = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(relatedLabels);
        assertEquals(set, relatedLabels);
    }
}
