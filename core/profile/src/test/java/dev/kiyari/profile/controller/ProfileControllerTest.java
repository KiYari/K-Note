package dev.kiyari.profile.controller;

import dev.kiyari.profile.model.entity.OAuthResource;
import dev.kiyari.profile.model.entity.Profile;
import dev.kiyari.profile.model.profile.EditDto;
import dev.kiyari.profile.model.profile.ListDto;
import dev.kiyari.profile.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {
    @InjectMocks
    private ProfileController controller;

    @Mock
    private ProfileService profileService;

    private List<Profile> profiles = new ArrayList<>();

    @BeforeEach
    public void setup() {
        Set<OAuthResource> set1 = new HashSet<>();
        set1.add(OAuthResource.GOOGLE);

        Set<OAuthResource> set3 = new HashSet<>();
        set3.add(OAuthResource.YANDEX);

        Set<OAuthResource> set2 = new HashSet<>();
        set2.add(OAuthResource.GOOGLE);
        set2.add(OAuthResource.YANDEX);

        profiles.add(new Profile(1L, "Cool David1", set1, LocalDateTime.now(), LocalDateTime.now()));
        profiles.add(new Profile(2L, "NiceBagger", set2, LocalDateTime.now(), LocalDateTime.now()));
        profiles.add(new Profile(3L, "GasDriver", set3, LocalDateTime.now(), LocalDateTime.now()));
    }

    private void assertFields(ListDto profile) {
        assertNotNull(profile.getDateCreated());
        assertNotNull(profile.getResources());
        assertNotNull(profile.getLastUpdated());
        assertNotNull(profile.getUsername());
        assertNotNull(profile.getId());
    }

    @Test
    public void readTest() {
        Long id = 1L;
        Profile profile = profiles.get(0);

        when(profileService.read(id)).thenReturn(profile);

        ResponseEntity<ListDto> response = controller.read(id);
        ListDto dto = response.getBody();

        assert dto != null;
        assertFields(dto);
    }

    @Test
    public void createTest() {
        Long id = 1L;
        Profile profile = profiles.get(0);
        EditDto dto = EditDto.parseObject(profile);

        when(profileService.save(dto)).thenReturn(profile);

        ResponseEntity<ListDto> response = controller.create(dto);
        ListDto dto2 = response.getBody();

        assert dto2 != null;
        assertFields(dto2);
    }

    @Test
    public void updateTest() {
        Long id = 1L;
        Profile profile = profiles.get(0);
        EditDto dto = EditDto.parseObject(profile);

        when(profileService.update(id, dto)).thenReturn(profile);

        ResponseEntity<ListDto> response = controller.update(id, dto);
        ListDto dto2 = response.getBody();

        assert dto2 != null;
        assertFields(dto2);
    }

    @Test
    public void deleteTest() {
        Long id = 1L;
        Profile profile = profiles.get(0);

        when(profileService.delete(id)).thenReturn(profile);

        ResponseEntity<ListDto> response = controller.delete(id);
        ListDto dto2 = response.getBody();

        assert dto2 != null;
        assertFields(dto2);
    }


}
