package com.lrcortizo.springframework.boot.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum FileExtension {
    JSON("JsonMapperBuilderService"), YAML("YamlMapperBuilderService");

    private final String mapperBuilderServiceClass;

    @Override
    public String toString() {
        return "." + this.name().toLowerCase();
    }
}
