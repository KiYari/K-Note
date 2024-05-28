package dev.kiyari.profile.service;

import dev.kiyari.base.exception.EntityAlreadyPresentException;
import dev.kiyari.base.exception.SaveEntityException;
import dev.kiyari.base.exception.UnexpectedException;
import dev.kiyari.profile.model.entity.Profile;
import dev.kiyari.profile.model.profile.EditDto;
import dev.kiyari.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Profile read(Long id) {
        return profileRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Profile save(EditDto dto) {
        if (dto != null) {
            dto.setDateCreated(LocalDateTime.now());
            dto.setLastUpdated(LocalDateTime.now());
            return profileRepository.save(Profile.parseEditDto(dto));
        }
        throw new UnexpectedException("Could not save Profile due to unexpected reasons.");
    }

    @Transactional
    public Profile update(Long id, EditDto dto) {
        if (!existsById(id)) {
            throw new SaveEntityException("There is no entity with such ID to update");
        }
        Profile existing = read(id);

        existing.setLastUpdated(dto.getLastUpdated());
        existing.setUsername(dto.getUsername());

        return profileRepository.save(existing);
    }

    private boolean existsById(Long id) {
        if (id == null || id < 0) {
            return false;
        }
        return profileRepository.existsById(id);
    }

    public Profile delete(Long id) {
        if(existsById(id)) {
            Profile profile = read(id);
            profileRepository.delete(profile);
            return profile;
        }
        throw new EntityAlreadyPresentException("Could not delete Profile due to unexpected reasons.");
    }
}
