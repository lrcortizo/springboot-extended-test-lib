package com.lrcortizo.springframework.boot.mock;

import com.lrcortizo.springframework.boot.configuration.properties.TestClassMapperProperties;
import com.lrcortizo.springframework.boot.mapper.MockMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Fail.fail;

@Component
public class MockCollection extends MockMapper {

    public MockCollection(final TestClassMapperProperties properties) {
        super(properties);
    }

    public <T> T givenClassYAML(final Class<T> type) {
        return this.mockClassFromYAML(type, StringUtils.EMPTY);
    }

    public <T> T givenClassYAML(final Class<T> type, final String label) {
        return this.mockClassFromYAML(type, label);
    }

    public <T> T givenClassJSON(final Class<T> type) {
        return this.mockClassFromJSON(type, StringUtils.EMPTY);
    }

    public <T> T givenClassJSON(final Class<T> type, final String label) {
        return this.mockClassFromJSON(type, label);
    }

    public <T> Collection<T> givenCollectionYAML(final Class<T> type) {
        return this.mockCollectionFromYAML(type, StringUtils.EMPTY);
    }

    public <T> Collection<T> givenCollectionYAML(final Class<T> type, final String label) {
        return this.mockCollectionFromYAML(type, label);
    }

    public <T> Collection<T> givenCollectionJSON(final Class<T> type) {
        return this.mockCollectionFromJSON(type, StringUtils.EMPTY);
    }

    public <T> Collection<T> givenCollectionJSON(final Class<T> type, final String label) {
        return this.mockCollectionFromJSON(type, label);
    }

    private <T> T mockClassFromYAML(final Class<T> type, final String label) {
        try {
            super.setLabel(label);
            return super.loadTestExpectedClass(type);
        } catch (final Exception e) {
            failOnException(type.getSimpleName(), e.getMessage());
        } finally {
            super.setLabel("");
        }
        return null;
    }

    private <T> T mockClassFromJSON(final Class<T> type, final String label) {
        try {
            super.setLabel(label);
            return super.loadJsonTestExpectedClass(type);
        } catch (final Exception e) {
            failOnException(type.getSimpleName(), e.getMessage());
        }
        return null;
    }

    private <T> Collection<T> mockCollectionFromYAML(final Class<T> type, final String label) {
        try {
            super.setLabel(label);
            return super.loadTestExpectedClassCollection(type);
        } catch (final Exception e) {
            failOnException(type.getSimpleName(), e.getMessage());
        } finally {
            super.setLabel("");
        }
        return List.of();
    }

    private <T> Collection<T> mockCollectionFromJSON(final Class<T> type, final String label) {
        try {
            super.setLabel(label);
            return super.loadJsonExpectedClassCollection(type);
        } catch (final Exception e) {
            failOnException(type.getSimpleName(), e.getMessage());
        } finally {
            super.setLabel("");
        }
        return List.of();
    }

    private static void failOnException(final String className, final String exception) {
        final String msg = "Mocked class " + className + ".class loading error -> " + exception;
        fail(msg);
    }
}
