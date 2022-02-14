package com.lrcortizo.springframework.boot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestContext {

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }
}
