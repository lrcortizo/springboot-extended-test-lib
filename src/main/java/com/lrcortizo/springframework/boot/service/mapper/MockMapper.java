package com.lrcortizo.springframework.boot.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrcortizo.springframework.boot.configuration.ClassMapperConfiguration;
import com.lrcortizo.springframework.boot.configuration.properties.ClassMapperProperties;
import com.lrcortizo.springframework.boot.service.mapper.objectmapper.ObjectMapperBuilderResolver;
import com.lrcortizo.springframework.boot.model.FileExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ClassMapperConfiguration.class, ObjectMapperBuilderResolver.class})
@Component
public class MockMapper {

    private final ClassMapperProperties classMapperProperties;
    private final ObjectMapperBuilderResolver objectMapperBuilderResolver;

    public MockMapper(final ClassMapperProperties classMapperProperties,
                      final ObjectMapperBuilderResolver objectMapperBuilderResolver) {
        this.classMapperProperties = classMapperProperties;
        this.objectMapperBuilderResolver = objectMapperBuilderResolver;
    }

    public <T> T loadJsonTestClass(final Class<T> type, final String label) throws IOException {
        return new ClassMapper(this.classMapperProperties, this.buildObjectMapper(FileExtension.JSON))
                .loadTestClass(type, label);
    }

    public <T> T loadYamlTestClass(final Class<T> type, final String label) throws IOException {
        return new ClassMapper(this.classMapperProperties, this.buildObjectMapper(FileExtension.YAML))
                .loadTestClass(type, label);
    }

    public <T> Collection<T> loadJsonTestClassCollection(final Class<T> type, final String label) throws IOException {
        return new ClassMapper(this.classMapperProperties, this.buildObjectMapper(FileExtension.JSON))
                .loadTestClassCollection(type, label);
    }

    public <T> Collection<T> loadYamlTestClassCollection(final Class<T> type, final String label) throws IOException {
        return new ClassMapper(this.classMapperProperties, this.buildObjectMapper(FileExtension.YAML))
                .loadTestClassCollection(type, label);
    }

    public <T> Boolean saveJsonFileObject(final Object object, final Class<T> type, final String label) {
        return new ClassMapper(this.classMapperProperties, this.buildObjectMapper(FileExtension.JSON))
                .writeFileObject(object, type, label);
    }

    public <T> Boolean saveYamlFileObject(final Object object, final Class<T> type, final String label) {
        return new ClassMapper(this.classMapperProperties, this.buildObjectMapper(FileExtension.YAML))
                .writeFileObject(object, type, label);
    }

    private ObjectMapper buildObjectMapper(final FileExtension fileExtension) {
        return this.objectMapperBuilderResolver.resolve(fileExtension.getMapperBuilderServiceClass())
                .buildObjectMapper();
    }
}
