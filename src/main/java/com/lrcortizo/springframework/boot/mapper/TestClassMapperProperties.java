package com.lrcortizo.springframework.boot.mapper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "w2m.test")
public class TestClassMapperProperties {
    String resourcesPath;
    String mapperExtension;
    String wordsJoiner;
    String collectionsLabel;
}
