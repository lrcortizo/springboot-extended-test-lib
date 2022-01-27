package com.lrcortizo.springframework.boot.mock;

import com.lrcortizo.springframework.boot.mapper.MockMapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.assertj.core.api.Fail.fail;

@Component
@NoArgsConstructor
public class MockCollection extends MockMapper {

    public <T> T givenClass(final Class<T> type) {
        return this.mockClassFromYAML(type, null);
    }

    public <T> T givenClassJSON(final Class<T> type) {
        return this.mockClassFromJSON(type);
    }

    public <T> Collection<T> givenCollection(final Class<T> type) {
        return this.mockCollectionFromYAML(type, null);
    }

    public <T> Collection<T> givenCollectionJSON(final Class<T> type) {
        return this.mockCollectionFromJSON(type, null);
    }

    public <T> Collection<T> givenCollection(final Class<T> type, final String label) {
        return this.mockCollectionFromYAML(type, label);
    }

    public <T> T givenClass(final Class<T> type, final String label) {
        return this.mockClassFromYAML(type, label);
    }

    private <T> Collection<T> mockCollectionFromYAML(final Class<T> type, final String label) {
        try {
            super.setLabel(label != null ? label : "");
            return super.loadTestExpectedClassCollection(type);
        } catch (final Exception e) {
            failOnException(type.getSimpleName(), e.getMessage());
        } finally {
            super.setLabel("");
        }
        return null;
    }

    private <T> Collection<T> mockCollectionFromJSON(final Class<T> type, final String label) {
        try {
            super.setLabel(label != null ? label : "");
            return super.loadJsonExpectedClassCollection(type);
        } catch (final Exception e) {
            failOnException(type.getSimpleName(), e.getMessage());
        } finally {
            super.setLabel("");
        }
        return null;
    }

    private <T> T mockClassFromYAML(final Class<T> type, final String label) {
        try {
            super.setLabel(label != null ? label : "");
            return super.loadTestExpectedClass(type);
        } catch (final Exception e) {
            failOnException(type.getSimpleName(), e.getMessage());
        } finally {
            super.setLabel("");
        }
        return null;
    }

    private <T> T mockClassFromJSON(final Class<T> type) {
        try {
            return super.loadJsonTestExpectedClass(type);
        } catch (final Exception e) {
            failOnException(type.getSimpleName(), e.getMessage());
        }
        return null;
    }

    private static void failOnException(final String className, final String exception) {
        final String msg = "Mocked class " + className + ".class loading error -> " + exception;
        fail(msg);
    }

}
