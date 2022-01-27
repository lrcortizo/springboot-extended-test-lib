package com.lrcortizo.springframework.boot.mapper;

public enum FileExtension {
    YAML, TXT, JSON;

    private static final String TEST_NAME = "test";
    private static final String EXPECTED_NAME = "expected";

    @Override
    public String toString() {
        return "." + this.name().toLowerCase();
    }

    public String fileTestSuffixExt(final String joiner) {
        return joiner + TEST_NAME + this;
    }

    public String fileExpectedSuffixExt(final String joiner) {
        return joiner + EXPECTED_NAME + this;
    }
}
