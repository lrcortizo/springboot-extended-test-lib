package com.lrcortizo.springframework.boot.configuration.properties;

import lombok.Data;
import lombok.experimental.NonFinal;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.custom.extended-test")
@ConstructorBinding
@Data
@NonFinal
public class ClassMapperProperties {

    String mocksPath;

    String wordsJoiner;

    String collectionsLabel;
}
