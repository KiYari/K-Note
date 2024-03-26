package dev.kiyari.note.service;

import dev.kiyari.note.model.entity.Label;
import dev.kiyari.note.model.entity.Note;
import dev.kiyari.note.model.label.EditDto;
import dev.kiyari.note.repository.LabelRepository;
import dev.kiyari.note.util.exception.CreateEntityException;
import dev.kiyari.note.util.exception.DeleteEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTests {

    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
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
    public void testReadLabel_ExistingId_ReturnsLabel() throws Exception {
        Long existingId = 0L;
        Label expectedLabel = labels.get(0);
        when(labelRepository.findById(existingId)).thenReturn(Optional.of(expectedLabel));

        Label actualLabel = labelService.read(existingId);

        assertNotNull(actualLabel);
        assertEquals(expectedLabel, actualLabel);
    }

    @Test
    public void testReadLabel_NonExistingId_ThrowsException() throws Exception {
        Long nonExistingId = 10L;
        when(labelRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> labelService.read(nonExistingId));
    }

    @Test
    public void testGetAllLabels_ReturnsListOfLabels() throws Exception {
        Set<Label> expectedLabels = new LinkedHashSet<>();
        expectedLabels.add(labels.get(0));
        expectedLabels.add(labels.get(1));
        when(labelRepository.findAll()).thenReturn(expectedLabels);

        Set<Label> actualLabels = labelService.getAll();

        assertNotNull(actualLabels);
        assertEquals(expectedLabels.size(), actualLabels.size());
        assertTrue(actualLabels.containsAll(expectedLabels));
    }

    @Test
    public void testSaveLabel_ValidDto_ReturnsSavedLabel() throws Exception {
        EditDto editDto = EditDto.parseObject(labels.get(0));
        Label expectedLabel = Label.parseEditDto(editDto);
        when(labelRepository.save(expectedLabel)).thenReturn(expectedLabel);

        Label actualLabel = labelService.save(editDto);

        assertNotNull(actualLabel);
        assertEquals(expectedLabel, actualLabel);
    }

    @Test
    public void testSaveLabel_NullDto_ThrowsException() throws Exception {
        assertThrows(CreateEntityException.class, () -> labelService.save(null));
    }

    @Test
    public void testDeleteLabel_ExistingId_DeletesLabel() throws Exception {
        Long existingId = 0L;
        Label labelToDelete = labels.get(0);
        when(labelRepository.existsById(existingId)).thenReturn(true);
        when(labelRepository.findById(existingId)).thenReturn(Optional.of(labelToDelete));

        labelService.delete(existingId);

        verify(labelRepository).delete(labelToDelete);
    }

    @Test
    public void testDeleteLabel_NonExistingId_ThrowsException() throws Exception {
        Long nonExistingId = 10L;
        when(labelRepository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(DeleteEntityException.class, () -> labelService.delete(nonExistingId));
    }
}