package com.lrcortizo.springframework.boot.mock;

import com.lrcortizo.springframework.boot.configuration.properties.TestClassMapperProperties;
import com.lrcortizo.springframework.boot.mapper.MockMapper;
import com.lrcortizo.springframework.boot.mapper.objectmapper.ObjectMapperBuilderResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Fail.fail;

@Component
@Slf4j
public class MockService extends MockMapper {

    public MockService(final TestClassMapperProperties testClassMapperProperties,
                       final ObjectMapperBuilderResolver objectMapperBuilderResolver) {
        super(testClassMapperProperties, objectMapperBuilderResolver);
    }

    public <T> T mockClassFromJSON(final Class<T> type) {
        return this.mockClassFromJSON(type, StringUtils.EMPTY);
    }

    public <T> T mockClassFromJSON(final Class<T> type, final String label) {
        try {
            return super.loadJsonTestClass(type, label);
        } catch (final IOException e) {
            failOnException(type.getSimpleName(), e);
        }
        return null;
    }

    public <T> T mockClassFromYAML(final Class<T> type) {
        return this.mockClassFromYAML(type, StringUtils.EMPTY);
    }

    public <T> T mockClassFromYAML(final Class<T> type, final String label) {
        try {
            return super.loadYamlTestClass(type, label);
        } catch (final IOException e) {
            failOnException(type.getSimpleName(), e);
        }
        return null;
    }

    public <T> Collection<T> mockCollectionFromJSON(final Class<T> type) {
        return this.mockCollectionFromJSON(type, StringUtils.EMPTY);
    }

    public <T> Collection<T> mockCollectionFromJSON(final Class<T> type, final String label) {
        try {
            return super.loadJsonTestClassCollection(type, label);
        } catch (final IOException e) {
            failOnException(type.getSimpleName(), e);
        }
        return List.of();
    }

    public <T> Collection<T> mockCollectionFromYAML(final Class<T> type) {
        return this.mockCollectionFromYAML(type, StringUtils.EMPTY);
    }

    public <T> Collection<T> mockCollectionFromYAML(final Class<T> type, final String label) {
        try {
            return super.loadYamlTestClassCollection(type, label);
        } catch (final IOException e) {
            failOnException(type.getSimpleName(), e);
        }
        return List.of();
    }

    private static void failOnException(final String className, final Exception exception) {
        final String msg = String.format("Mocked class %s.class loading error -> %s", className, exception.getMessage());
        log.error(msg, exception);
        fail(msg);
    }
}
