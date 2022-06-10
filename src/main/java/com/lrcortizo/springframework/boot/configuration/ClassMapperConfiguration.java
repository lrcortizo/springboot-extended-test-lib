package com.lrcortizo.springframework.boot.configuration;

import com.lrcortizo.springframework.boot.configuration.properties.ClassMapperProperties;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Value
@NonFinal
@EnableConfigurationProperties(ClassMapperProperties.class)
public class ClassMapperConfiguration {

    ClassMapperProperties classMapperProperties;
}
