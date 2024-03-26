package dev.kiyari.note.controller;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.label.EditDto;
import dev.kiyari.note.model.label.ListDto;
import dev.kiyari.note.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LabelControllerTests {

    @InjectMocks
    private LabelController labelController;

    @Mock
    private LabelService labelService;
    private List<Label> labels = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Set<Note> emptyNotes = new HashSet<>();
        Set<Label> emptyLabels = new HashSet<>();

        LocalDateTime now = LocalDateTime.now();

        Label mockLabel1 = new Label(1L, "Captivating Cosmos", "Explore the wonders of the universe", emptyNotes, emptyLabels);

        Note mockNote1 = new Note(null, "Black Holes", "Black holes are regions in spacetime where gravity is so strong that nothing, not even light, can escape",
                "A black hole is a place in spacetime where gravity pulls so much that even light cannot escape. This is because gravity is caused by mass, and a black hole has a very large mass in a very small space. The result is a gravitational pull so strong that nothing can get out.", emptyNotes, emptyLabels, now, now);
        mockLabel1.addRelatedNote(mockNote1);

        Label mockLabel2 = new Label(2L, "Flourishing Flora", "Discover the diversity of plant life", emptyNotes, emptyLabels);

        Note mockNote2 = new Note(null, "Rainforests", "Rainforests are the Earth's lungs, teeming with life",
                "Rainforests are the Earth's most biodiverse habitats. They cover about 2% of the Earth's surface but are home to more than half of the world's plant and animal species.", emptyNotes, emptyLabels, now, now);
        mockLabel2.addRelatedNote(mockNote2);

        Label mockLabel3 = new Label(3L, "Mesmerizing Music", "Delve into the world of sound", emptyNotes, emptyLabels);

        this.labels.add(mockLabel1);
        this.labels.add(mockLabel2);
        this.labels.add(mockLabel3);
    }

    @Test
    public void testGetAllLabels_ReturnsListOfLabels() throws Exception {
        Set<Label> expectedLabels = new HashSet<>();
        expectedLabels.add(labels.get(0));
        expectedLabels.add(labels.get(1));
        expectedLabels.add(labels.get(2));

        when(labelService.getAll()).thenReturn(expectedLabels);

        ResponseEntity<Set<ListDto>> response = labelController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Set<ListDto> actualListDtos = response.getBody();
        assertNotNull(actualListDtos);
        assertEquals(expectedLabels.size(), actualListDtos.size());

        for (ListDto actualDto : actualListDtos) {
            assertTrue(expectedLabels.stream().anyMatch(label -> label.getId().equals(actualDto.getId())));
        }
    }

    @Test
    public void testGetLabelById_ExistingId_ReturnsLabel() {
        Long existingId = 1L;
        when(labelService.read(existingId)).thenReturn(labels.get(0));

        ResponseEntity<ListDto> response = labelController.read(existingId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ListDto actualDto = response.getBody();
        assertNotNull(actualDto);

        assertEquals(existingId, actualDto.getId());
        assertEquals(existingId, actualDto.getId());
        assertEquals(labels.get(0).getTitle(), actualDto.getTitle());
        assertEquals(labels.get(0).getDescription(), actualDto.getDescription());
    }

    @Test
    public void testGetLabelById_NonExistingId_ReturnsNoBodyObject() throws Exception {
        Long nonExistingId = 10L;
        when(labelService.read(nonExistingId)).thenReturn(null);

        ResponseEntity<ListDto> response = labelController.read(nonExistingId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.ok(new ListDto()), response);
    }

    @Test
    public void testCreateLabel_ValidDto_ReturnsCreatedLabel() throws Exception {
        EditDto newDto = EditDto.parseObject(labels.get(2));
        Label expectedLabel = Label.parseEditDto(newDto);
        when(labelService.save(newDto)).thenReturn(expectedLabel);

        ResponseEntity<EditDto> response = labelController.create(newDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        EditDto actualDto = response.getBody();
        assertNotNull(actualDto);

        assertEquals(expectedLabel.getTitle(), actualDto.getTitle());
        assertEquals(expectedLabel.getDescription(), actualDto.getDescription());
    }

    @Test
    public void testUpdateLabel_ValidDto_ReturnsUpdatedLabel(){
        EditDto updateDto = EditDto.parseObject(labels.get(1));
        Label expectedLabel = Label.parseEditDto(updateDto);
        when(labelService.update(updateDto)).thenReturn(expectedLabel);

        ResponseEntity<EditDto> response = labelController.update(updateDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        EditDto actualDto = response.getBody();
        assertNotNull(actualDto);

        assertEquals(expectedLabel.getId(), actualDto.getId());
        assertEquals(expectedLabel.getTitle(), actualDto.getTitle());
        assertEquals(expectedLabel.getDescription(), actualDto.getDescription());
    }

    @Test
    public void testDeleteLabel_ExistingId_ReturnsDeletedLabel(){
        Long existingId = 1L;
        Label deletedLabel = labels.get(1);
        when(labelService.delete(existingId)).thenReturn(deletedLabel);

        ResponseEntity<ListDto> response = labelController.delete(existingId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ListDto actualDto = response.getBody();
        assertNotNull(actualDto);

        assertEquals(deletedLabel.getId(), actualDto.getId());
        assertEquals(deletedLabel.getTitle(), actualDto.getTitle());
        assertEquals(deletedLabel.getDescription(), actualDto.getDescription());
    }

}
