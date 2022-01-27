package com.lrcortizo.springframework.boot.configuration;

import com.lrcortizo.springframework.boot.configuration.properties.TestClassMapperProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(TestClassMapperProperties.class)
public class TestClassMapperConfiguration {
}
