package com.lrcortizo.springframework.boot.service;

import com.lrcortizo.springframework.boot.configuration.properties.ClassMapperProperties;
import com.lrcortizo.springframework.boot.service.mapper.MockMapper;
import com.lrcortizo.springframework.boot.service.mapper.objectmapper.ObjectMapperBuilderResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Fail.fail;

@Component
public class MockService extends MockMapper {

    public MockService(final ClassMapperProperties classMapperProperties,
                       final ObjectMapperBuilderResolver objectMapperBuilderResolver) {
        super(classMapperProperties, objectMapperBuilderResolver);
    }

    public <T> T mockClassFromJson(final Class<T> type) {
        return this.mockClassFromJson(type, StringUtils.EMPTY);
    }

    public <T> T mockClassFromJson(final Class<T> type, final String label) {
        try {
            return super.loadJsonTestClass(type, label);
        } catch (final RuntimeException | IOException e) {
            failOnException(type.getSimpleName(), e.getMessage());
        }
        return null;
    }

    public <T> T mockClassFromYaml(final Class<T> type) {
        return this.mockClassFromYaml(type, StringUtils.EMPTY);
    }

    public <T> T mockClassFromYaml(final Class<T> type, final String label) {
        try {
            return super.loadYamlTestClass(type, label);
        } catch (final RuntimeException | IOException e) {
            failOnException(type.getSimpleName(), e.getMessage());
        }
        return null;
    }

    public <T> Collection<T> mockCollectionFromJson(final Class<T> type) {
        return this.mockCollectionFromJson(type, StringUtils.EMPTY);
    }

    public <T> Collection<T> mockCollectionFromJson(final Class<T> type, final String label) {
        try {
            return super.loadJsonTestClassCollection(type, label);
        } catch (final RuntimeException | IOException e) {
            failOnException(type.getSimpleName(), e.getMessage());
        }
        return List.of();
    }

    public <T> Collection<T> mockCollectionFromYaml(final Class<T> type) {
        return this.mockCollectionFromYaml(type, StringUtils.EMPTY);
    }

    public <T> Collection<T> mockCollectionFromYaml(final Class<T> type, final String label) {
        try {
            return super.loadYamlTestClassCollection(type, label);
        } catch (final RuntimeException | IOException e) {
            failOnException(type.getSimpleName(), e.getMessage());
        }
        return List.of();
    }

    public <T> Boolean saveJsonMockClass(final Object object, final Class<T> type) {
        return this.saveJsonMockClass(object, type, StringUtils.EMPTY);
    }

    public <T> Boolean saveJsonMockClass(final Object object, final Class<T> type, final String label) {
        try {
            return super.saveJsonFileObject(object, type, label);
        } catch (final RuntimeException e) {
            failOnException(type.getSimpleName(), e.getMessage());
        }
        return Boolean.FALSE;
    }

    public <T> Boolean saveYamlMockClass(final Object object, final Class<T> type) {
        return this.saveYamlMockClass(object, type, StringUtils.EMPTY);
    }

    public <T> Boolean saveYamlMockClass(final Object object, final Class<T> type, final String label) {
        try {
            return super.saveYamlFileObject(object, type, label);
        } catch (final RuntimeException e) {
            failOnException(type.getSimpleName(), e.getMessage());
        }
        return Boolean.FALSE;
    }

    private static void failOnException(final String className, final String exception) {
        final String msg = String.format("Mocked class %s.class loading error -> %s", className, exception);
        fail(msg);
    }
}
