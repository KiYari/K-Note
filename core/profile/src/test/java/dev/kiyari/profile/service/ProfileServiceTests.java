package dev.kiyari.profile.service;

import dev.kiyari.profile.model.entity.OAuthResource;
import dev.kiyari.profile.model.entity.Profile;
import dev.kiyari.profile.model.profile.EditDto;
import dev.kiyari.profile.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTests {
    @Mock
    protected ProfileRepository profileRepository;
    @InjectMocks
    protected ProfileService profileService;

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

    private void assertFields(Profile profile) {
        assertNotNull(profile.getDateCreated());
        assertNotNull(profile.getResources());
        assertNotNull(profile.getLastUpdated());
        assertNotNull(profile.getUsername());
        assertNotNull(profile.getId());
    }

    @Test
    public void testSaveProfile_shouldReturnValidProfile() {
        Profile profile = profiles.get(0);

        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        Profile saved = profileService.save(EditDto.parseObject(profile));

        assertFields(saved);
    }

    @Test
    public void testReadProfile_shouldReturnValidProfile() {
        Profile profile = profiles.get(0);
        Long id = 1L;

        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));

        assertFields(profileService.read(id));
    }

    @Test
    public void testReadProfile_shouldReturnExceptionWhenProfileDoesNotExist() {
        Long nonExistingId = 6L;

        when(profileRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> profileService.read(nonExistingId));
    }

    @Test
    public void testUpdateProfile_shouldReturnValidProfile() {
        Long id = 1L;
        Profile profile = profiles.get(0);

        when(profileRepository.existsById(id)).thenReturn(true);
        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile saved = profileService.update(id, EditDto.parseObject(profile));

        assertFields(saved);
    }

    @Test
    public void testUpdateProfile_shouldReturnExceptionWhenProfileDoesNotExist() {
        Long nonExistingId = 6L;
        Profile profile = profiles.get(0);

        when(profileRepository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> profileService.update(nonExistingId, EditDto.parseObject(profile)));
    }

    @Test
    public void testDeleteProfile_shouldReturnValidProfile() {
        Long id = 1L;
        Profile profile = profiles.get(0);

        when(profileRepository.existsById(id)).thenReturn(true);
        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));

        Profile deleted = profileService.delete(id);

        verify(profileRepository).delete(deleted);
        assertFields(deleted);
    }

    @Test
    public void testDeleteProfile_shouldReturnExceptionWhenProfileDoesNotExist() {
        Long nonExistingId = 6L;

        when(profileRepository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> profileService.delete(nonExistingId));
    }

}
