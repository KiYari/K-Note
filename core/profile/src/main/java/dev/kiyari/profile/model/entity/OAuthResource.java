package dev.kiyari.profile.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum OAuthResource {
    GOOGLE("Google"),
    YANDEX("Yandex");

    private final String name;
}
