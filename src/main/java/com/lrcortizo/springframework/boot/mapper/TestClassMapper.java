package com.lrcortizo.springframework.boot.mapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import com.lrcortizo.springframework.boot.configuration.properties.TestClassMapperProperties;
import com.lrcortizo.springframework.boot.model.catalog.FileExtension;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class TestClassMapper {

    private static final String DEFAULT_RESOURCES_PATH = "src\\test\\resources\\mocks";
    private static final FileExtension DEFAULT_FILE_EXTENSION = FileExtension.JSON;
    private static final String DEFAULT_WORDS_JOINER = "_";

    private final Path resourcesPath;
    private FileExtension fileExtension;
    private final String wordsJoiner;
    private ObjectMapper objectMapper;

    TestClassMapper(@NonNull final TestClassMapperProperties properties) {
        this.resourcesPath = Paths.get(Optional.ofNullable(properties.getResourcesPath())
                .orElse(DEFAULT_RESOURCES_PATH));
        this.fileExtension = FileExtension.valueOf(Optional.ofNullable(properties.getFileExtension())
                .orElse(DEFAULT_FILE_EXTENSION.name()).toUpperCase());
        this.wordsJoiner = Optional.ofNullable(properties.getWordsJoiner()).orElse(DEFAULT_WORDS_JOINER);
        this.objectMapper = this.buildObjectMapper();
    }

    public <T> T loadTestClass(final Class<T> type, final String label) throws IOException {
        return this.objectMapper.readValue(this.loadFile(type, label, Boolean.FALSE), type);
    }

    public <T> T loadTestClass(final Class<T> type, final String label, final FileExtension fileExtension)
            throws IOException {
        this.fileExtension = fileExtension;
        this.objectMapper = this.buildObjectMapper();
        return this.objectMapper.readValue(this.loadFile(type, label, Boolean.FALSE), type);
    }

    public <T> Collection<T> loadTestClassCollection(final Class<T> type, final String label) throws IOException {
        return this.objectMapper.readValue(this.loadFile(type, label, Boolean.TRUE),
                this.objectMapper.getTypeFactory().constructCollectionType(Collection.class, type));
    }

    public <T> Collection<T> loadTestClassCollection(final Class<T> type, final String label,
                                                     final FileExtension fileExtension) throws IOException {
        this.fileExtension = fileExtension;
        this.objectMapper = this.buildObjectMapper();
        return this.objectMapper.readValue(this.loadFile(type, label, Boolean.TRUE),
                this.objectMapper.getTypeFactory().constructCollectionType(Collection.class, type));
    }

    public <T> Boolean saveFileObject(final Object object, final String label, final Class<T> type) {
        Objects.requireNonNull(object);
        final File file = this.loadFile(type, label, (object instanceof Collection<?>));
        log.info("Write test class resource file [{}] from collection > {}", file.getName(), type.getSimpleName());
        return this.saveFileObject(object, file);
    }

    private <T> File loadFile(final Class<T> tClass, final String label, final Boolean isCollection) {
        Objects.requireNonNull(tClass);
        final String fileName = this.fileNameBuild(tClass.getSimpleName(), label, isCollection);
        log.info("Parse test class {} [{}] from resource file > {}",
                Boolean.TRUE.equals(isCollection) ? "collection" : "object", tClass.getSimpleName(), fileName);
        return new File(Paths.get(this.resourcesPath.toString(), fileName).toFile().getAbsolutePath());
    }

    private Boolean saveFileObject(final Object object, final File file) {
        try {
            this.objectMapper.writeValue(file, object);
            return Boolean.TRUE;
        } catch (final Exception ex) {
            log.error("Unable to write the test class resource file [{}]", ex.getMessage());
        }
        return Boolean.FALSE;
    }

    private String fileNameBuild(final String className, final String label, final Boolean isCollection) {
        return className + (Boolean.TRUE.equals(isCollection) ? "s" : StringUtils.EMPTY)
                + ((label == null || label.trim().isEmpty()) ? StringUtils.EMPTY : this.wordsJoiner + label.trim());
    }

    private ObjectMapper buildObjectMapper() {
        return this.fileExtension == FileExtension.YAML ? buildYamlObjectMapper() : buildJsonObjectMapper();
    }

    private static ObjectMapper buildJsonObjectMapper() {
        final JsonFactory jsonFactory = new JsonFactory();
        return new ObjectMapper(jsonFactory).findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private static ObjectMapper buildYamlObjectMapper() {
        final YAMLFactory yamlFactory = new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER);
        return new ObjectMapper(yamlFactory).findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}


