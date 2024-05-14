package dev.kiyari.profile.model.profile;

import dev.kiyari.profile.model.entity.OAuthResource;
import dev.kiyari.profile.model.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class ListDto {
    private Long id;
    private String username;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private Set<OAuthResource> resources;

    public static ListDto parseObject(Profile profile) {
        return ListDto.builder()
                .id(profile.getId())
                .username(profile.getUsername())
                .dateCreated(profile.getDateCreated())
                .lastUpdated(profile.getLastUpdated())
                .resources(profile.getResources())
                .build();
    }
}
