package com.lrcortizo.springframework.boot.ouput;

public class TestOutput {
    public static String msgNull(final String name) {
        return "Expected " + name + " null value.";
    }

    public static String msgUnMatch(final String name) {
        return name + " unmatched.";
    }

    public static String msgEnum(final String name) {
        return "Invalid " + name + " Enumeration.";
    }

    public static String msgEmptyList(final String name) {
        return name + " is not an empty List";
    }

    public static String msgEmpty(final String name) {
        return name + " is not an empty Object";
    }

}
