package dev.kiyari.profile.service;

import dev.kiyari.profile.model.entity.OAuthResource;
import dev.kiyari.profile.model.entity.Profile;
import dev.kiyari.profile.repository.ProfileRepository;
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
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ProfileService {
    @Mock
    private ProfileRepository profileRepository;
    @InjectMocks
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

    @Test
    public void testSaveProfile_shouldReturnValidProfile() {

    }

}
