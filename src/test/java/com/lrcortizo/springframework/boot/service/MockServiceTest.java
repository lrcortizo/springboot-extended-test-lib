package com.lrcortizo.springframework.boot.service;

import com.lrcortizo.springframework.boot.service.mapper.MockMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = MockService.class)
class MockServiceTest {

    private static final String LABEL = "label";

    private static final String TEST_EXPECTED = "Expected";

    private final MockService mockService;

    @Autowired
    MockServiceTest(final MockService mockService) {
        this.mockService = spy(mockService);
    }

    @Test
    void mockClassFromJson_should_return_class() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        doReturn(TEST_EXPECTED).when((MockMapper) this.mockService).loadJsonTestClass(givenType, "");

        // When
        final String result = this.mockService.mockClassFromJson(givenType);

        // Then
        assertEquals(TEST_EXPECTED, result);
    }

    @Test
    void mockClassFromJson_with_label_should_return_class() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        doReturn(TEST_EXPECTED).when((MockMapper) this.mockService).loadJsonTestClass(givenType, LABEL);

        // When
        final String result = this.mockService.mockClassFromJson(givenType, LABEL);

        // Then
        assertEquals(TEST_EXPECTED, result);
    }

    @Test
    void mockClassFromYaml_should_return_class() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        doReturn(TEST_EXPECTED).when((MockMapper) this.mockService).loadYamlTestClass(givenType, "");

        // When
        final String result = this.mockService.mockClassFromYaml(givenType);

        // Then
        assertEquals(TEST_EXPECTED, result);
    }

    @Test
    void mockClassFromYaml_with_label_should_return_class() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        doReturn(TEST_EXPECTED).when((MockMapper) this.mockService).loadYamlTestClass(givenType, LABEL);

        // When
        final String result = this.mockService.mockClassFromYaml(givenType, LABEL);

        // Then
        assertEquals(TEST_EXPECTED, result);
    }

    @Test
    void mockCollectionFromJson_should_return_collection() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        final List<String> expected = List.of(TEST_EXPECTED);
        doReturn(expected).when((MockMapper) this.mockService).loadJsonTestClassCollection(givenType, "");

        // When
        final Collection<String> result = this.mockService.mockCollectionFromJson(givenType);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void mockCollectionFromJson_with_label_should_return_collection() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        final List<String> expected = List.of(TEST_EXPECTED);
        doReturn(expected).when((MockMapper) this.mockService).loadJsonTestClassCollection(givenType, LABEL);

        // When
        final Collection<String> result = this.mockService.mockCollectionFromJson(givenType, LABEL);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void mockCollectionFromYaml_should_return_collection() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        final List<String> expected = List.of(TEST_EXPECTED);
        doReturn(expected).when((MockMapper) this.mockService).loadYamlTestClassCollection(givenType, "");

        // When
        final Collection<String> result = this.mockService.mockCollectionFromYaml(givenType);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void mockCollectionFromYaml_with_label_should_return_collection() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        final List<String> expected = List.of(TEST_EXPECTED);
        doReturn(expected).when((MockMapper) this.mockService).loadYamlTestClassCollection(givenType, LABEL);

        // When
        final Collection<String> result = this.mockService.mockCollectionFromYaml(givenType, LABEL);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void mockClassFromJson_should_fail_when_exception() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        doThrow(IOException.class).when((MockMapper) this.mockService).loadJsonTestClass(givenType, "");

        // When
        // Then
        assertThrows(AssertionError.class, () -> this.mockService.mockClassFromJson(givenType));
    }

    @Test
    void mockClassFromYaml_should_fail_when_exception() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        doThrow(IOException.class).when((MockMapper) this.mockService).loadYamlTestClass(givenType, "");

        // When
        // Then
        assertThrows(AssertionError.class, () -> this.mockService.mockClassFromYaml(givenType));
    }

    @Test
    void mockCollectionFromJson_should_fail_when_exception() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        doThrow(IOException.class).when((MockMapper) this.mockService).loadJsonTestClassCollection(givenType, "");

        // When
        // Then
        assertThrows(AssertionError.class, () -> this.mockService.mockCollectionFromJson(givenType));
    }

    @Test
    void mockCollectionFromYaml_should_fail_when_exception() throws IOException {
        // Given
        final Class<String> givenType = String.class;
        doThrow(IOException.class).when((MockMapper) this.mockService).loadYamlTestClassCollection(givenType, "");

        // When
        // Then
        assertThrows(AssertionError.class, () -> this.mockService.mockCollectionFromYaml(givenType));
    }
}
