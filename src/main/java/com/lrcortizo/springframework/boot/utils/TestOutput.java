package com.lrcortizo.springframework.boot.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestOutput {

    public String msgNull(final String name) {
        return "Expected " + name + " null value.";
    }

    public String msgUnMatch(final String name) {
        return name + " unmatched.";
    }

    public String msgEnum(final String name) {
        return "Invalid " + name + " Enumeration.";
    }

    public String msgEmptyCollection(final String name) {
        return name + " is not an empty Collection";
    }

    public String msgEmpty(final String name) {
        return name + " is not an empty Object";
    }
}
