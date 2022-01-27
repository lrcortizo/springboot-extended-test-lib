package com.lrcortizo.springframework.boot.model.catalog;

public enum FileExtension {
    YAML, TXT, JSON;

    @Override
    public String toString() {
        return "." + this.name().toLowerCase();
    }
}
