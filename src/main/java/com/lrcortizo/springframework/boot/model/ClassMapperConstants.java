package com.lrcortizo.springframework.boot.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassMapperConstants {

    public static final String DEFAULT_RESOURCES_PATH = "src/test/resources/mocks";

    public static final String DEFAULT_WORDS_JOINER = "_";

    public static final String DEFAULT_COLLECTIONS_LABEL = "s";
}
