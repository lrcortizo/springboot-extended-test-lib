package com.lrcortizo.springframework.boot.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutputUtilsTest {

    @Test
    void msgNull_should_return_msg() {
        // Given
        final String givenName = "test_name";
        final String expected = "Expected " + givenName + " null value";

        // When
        final String result = OutputUtils.msgNull(givenName);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void msgUnMatch_should_return_msg() {
        // Given
        final String givenName = "test_name";
        final String expected = givenName + " unmatched";

        // When
        final String result = OutputUtils.msgUnMatch(givenName);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void msgEnum_should_return_msg() {
        // Given
        final String givenName = "test_name";
        final String expected = "Invalid " + givenName + " Enumeration";

        // When
        final String result = OutputUtils.msgEnum(givenName);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void msgEmptyCollection_should_return_msg() {
        // Given
        final String givenName = "test_name";
        final String expected = givenName + " is not an empty Collection";

        // When
        final String result = OutputUtils.msgEmptyCollection(givenName);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void msgEmpty_should_return_msg() {
        // Given
        final String givenName = "test_name";
        final String expected = givenName + " is not an empty Object";

        // When
        final String result = OutputUtils.msgEmpty(givenName);

        // Then
        assertEquals(expected, result);
    }
}
