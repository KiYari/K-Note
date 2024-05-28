package dev.kiyari.profile.model.entity;


import dev.kiyari.note.model.BasicEntity;
import dev.kiyari.profile.model.profile.EditDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "profile")
@NoArgsConstructor
@Getter
public class Profile extends BasicEntity {
    @Builder
    public Profile(Long id, @NonNull String username, Set<OAuthResource> resources, LocalDateTime dateCreated, LocalDateTime lastUpdated) {
        super(id);
        this.username = username.trim();
        this.resources = resources;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    @Column(name = "username")
    @Setter
    private String username;

    private LocalDateTime dateCreated;
    @Setter
    private LocalDateTime lastUpdated;

    @Enumerated
    @ElementCollection(targetClass = OAuthResource.class)
    @CollectionTable(name = "profile_oauth", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "oauth")
    private Set<OAuthResource> resources;

    public static Profile parseEditDto(EditDto dto) {
        return Profile.builder()
                .username(dto.getUsername())
                .dateCreated(dto.getDateCreated())
                .lastUpdated(dto.getLastUpdated())
                .resources(dto.getResources())
                .build();
    }
}
