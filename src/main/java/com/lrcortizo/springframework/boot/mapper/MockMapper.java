package com.lrcortizo.springframework.boot.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrcortizo.springframework.boot.configuration.TestClassMapperConfiguration;
import com.lrcortizo.springframework.boot.configuration.properties.TestClassMapperProperties;
import com.lrcortizo.springframework.boot.mapper.objectmapper.ObjectMapperBuilderResolver;
import com.lrcortizo.springframework.boot.model.catalog.FileExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestClassMapperConfiguration.class, ObjectMapperBuilderResolver.class})
@Component
public class MockMapper {

    private final TestClassMapperProperties testClassMapperProperties;
    private final ObjectMapperBuilderResolver objectMapperBuilderResolver;

    public MockMapper(final TestClassMapperProperties testClassMapperProperties,
                      final ObjectMapperBuilderResolver objectMapperBuilderResolver) {
        this.testClassMapperProperties = testClassMapperProperties;
        this.objectMapperBuilderResolver = objectMapperBuilderResolver;
    }

    public <T> T loadJsonTestClass(final Class<T> type, final String label) throws IOException {
        return new TestClassMapper(this.testClassMapperProperties, this.buildObjectMapper(FileExtension.JSON))
                .loadTestClass(type, label);
    }

    public <T> T loadYamlTestClass(final Class<T> type, final String label) throws IOException {
        return new TestClassMapper(this.testClassMapperProperties, this.buildObjectMapper(FileExtension.YAML))
                .loadTestClass(type, label);
    }

    public <T> Collection<T> loadJsonTestClassCollection(final Class<T> type, final String label) throws IOException {
        return new TestClassMapper(this.testClassMapperProperties, this.buildObjectMapper(FileExtension.JSON))
                .loadTestClassCollection(type, label);
    }

    public <T> Collection<T> loadYamlTestClassCollection(final Class<T> type, final String label) throws IOException {
        return new TestClassMapper(this.testClassMapperProperties, this.buildObjectMapper(FileExtension.YAML))
                .loadTestClassCollection(type, label);
    }

    public <T> Boolean saveJsonFileObject(final Object object, final Class<T> type, final String label) {
        return new TestClassMapper(this.testClassMapperProperties, this.buildObjectMapper(FileExtension.JSON))
                .saveFileObject(object, type, label);
    }

    public <T> Boolean saveYamlFileObject(final Object object, final Class<T> type, final String label) {
        return new TestClassMapper(this.testClassMapperProperties, this.buildObjectMapper(FileExtension.YAML))
                .saveFileObject(object, type, label);
    }

    private ObjectMapper buildObjectMapper(final FileExtension fileExtension) {
        return this.objectMapperBuilderResolver.resolve(fileExtension.getMapperBuilderServiceClass())
                .buildObjectMapper();
    }
}
