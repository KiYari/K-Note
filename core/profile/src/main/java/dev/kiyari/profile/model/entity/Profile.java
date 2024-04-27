package dev.kiyari.profile.model.entity;

import dev.kiyari.note.model.BasicEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Profile extends BasicEntity {
    @Column(name = "username")
    private String username;

    @Enumerated
    @ElementCollection(targetClass = OAuthResource.class)
    @CollectionTable(name = "profile_oauth", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "oauth")
    private Set<OAuthResource> resources;
}
