package dev.kiyari.note.controller;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class NoteControllerTests {
    @InjectMocks
    private NoteController noteController;

    @Mock
    private NoteService noteService;

    private List<Note> notes = new ArrayList<>();

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
    }

    private void assertNoteFields(ListDto dto) {
        assertNotNull(dto.getId());
        assertNotNull(dto.getLabel());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getNote());
        assertNotNull(dto.getDateCreated());
        assertNotNull(dto.getLastUpdated());
    }

    private void assertNoteFields(EditDto dto) {
        assertNotNull(dto.getId());
        assertNotNull(dto.getLabel());
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

        ResponseEntity<EditDto> response = noteController.create(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        EditDto responseDto = response.getBody();
        assert responseDto != null;
        assertNoteFields(responseDto);
    }

    @Test
    public void updateTest() {
        Long id = 2L;
        EditDto dto = EditDto.builder() // ... (set values for EditDto)
                .build();
        Note updatedNote = notes.get(1); // Assuming noteService.update() returns the updated note
        Mockito.when(noteService.update(dto)).thenReturn(updatedNote);

        ResponseEntity<EditDto> response = noteController.update(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        EditDto responseDto = response.getBody();
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
}
