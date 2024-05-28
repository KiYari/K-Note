package dev.kiyari.profile.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OAuthResource {
    GOOGLE("Google"),
    YANDEX("Yandex");

    private final String name;
}
