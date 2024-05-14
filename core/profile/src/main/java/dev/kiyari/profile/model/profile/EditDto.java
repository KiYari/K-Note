package dev.kiyari.profile.model.profile;


import dev.kiyari.profile.model.entity.OAuthResource;
import dev.kiyari.profile.model.entity.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class EditDto {
    private String username;
    @Setter
    private LocalDateTime dateCreated;
    @Setter
    private LocalDateTime lastUpdated;
    private Set<OAuthResource> resources;

    public static EditDto parseObject(Profile profile) {
        return EditDto.builder()
                .username(profile.getUsername())
                .dateCreated(profile.getDateCreated())
                .lastUpdated(profile.getLastUpdated())
                .resources(profile.getResources())
                .build();
    }
}
