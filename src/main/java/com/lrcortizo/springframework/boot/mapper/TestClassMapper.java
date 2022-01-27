package com.lrcortizo.springframework.boot.mapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import com.lrcortizo.springframework.boot.configuration.properties.TestClassMapperProperties;
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

    private static final String DEFAULT_TEST_RESOURCES = "src\\test\\resources\\mocks";
    private static final Feature DEFAULT_DISABLED_FEATURE = Feature.WRITE_DOC_START_MARKER;
    private static final SerializationFeature DEFAULT_DATE_SER = SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
    private static final FileExtension DEFAULT_FILE_EXTENSION = FileExtension.YAML;

    @NonNull
    private final String label;
    private final String wordsJoiner;
    private final Path testResourcePath;
    private ObjectMapper mapper;
    private FileExtension fileExtension;

    public TestClassMapper() {
        this(StringUtils.EMPTY);
    }

    TestClassMapper(@NonNull final String label) {
        this(label, new TestClassMapperProperties());
    }

    TestClassMapper(@NonNull final String label, @NonNull final TestClassMapperProperties properties) {
        super();
        this.label = label;
        this.fileExtension = FileExtension.valueOf(Optional.ofNullable(properties.getMapperExtension())
                .orElse(DEFAULT_FILE_EXTENSION.name()).toUpperCase());
        this.testResourcePath = Paths.get(Optional.ofNullable(properties.getResourcesPath())
                .orElse(DEFAULT_TEST_RESOURCES));
        this.wordsJoiner = Optional.ofNullable(properties.getWordsJoiner()).orElse("_");
        this.mapper = this.buildObjectMapper();
    }

    public <T> Collection<T> loadTestClassCollection(final Class<T> type) throws IOException {
        return this.mapper.readValue(this.loadFile(type, Boolean.TRUE, Boolean.TRUE),
                this.mapper.getTypeFactory().constructCollectionType(Collection.class, type));
    }

    public <T> T loadTestClass(final Class<T> type) throws IOException {
        return this.mapper.readValue(this.loadFile(type, Boolean.FALSE, Boolean.TRUE), type);
    }

    public <T> Collection<T> loadTestExpectedClassCollection(final Class<T> type) throws IOException {
        return this.mapper.readValue(this.loadFile(type, Boolean.TRUE, Boolean.FALSE),
                this.mapper.getTypeFactory().constructCollectionType(Collection.class, type));
    }

    public <T> T loadTestExpectedClass(final Class<T> type) throws IOException {
        return this.mapper.readValue(this.loadFile(type, Boolean.FALSE, Boolean.FALSE), type);
    }

    public <T> T loadTestExpectedClass(final Class<T> type, final FileExtension fileExtension) throws IOException {
        this.fileExtension = fileExtension;
        this.mapper = this.buildObjectMapper();
        return this.mapper.readValue(this.loadFile(type, Boolean.FALSE, Boolean.FALSE), type);
    }

    public <T> Collection<T> loadExpectedClassCollection(final Class<T> type, final FileExtension fileExtension) throws IOException {
        this.fileExtension = fileExtension;
        this.mapper = this.buildObjectMapper();
        return this.mapper.readValue(this.loadFile(type, Boolean.TRUE, Boolean.FALSE),
                this.mapper.getTypeFactory().constructCollectionType(Collection.class, type));
    }

    public <T> Boolean saveFileObject(final Object object, final Class<T> type, final Boolean isTest) {
        Objects.requireNonNull(object);
        final File file = this.loadFile(type, (object instanceof Collection<?>), isTest);
        log.info("Write test class resource file [{}] from collection > {}", file.getName(), type.getSimpleName());
        return this.saveFileObject(object, file);
    }

    private <T> File loadFile(final Class<T> tClass, final Boolean isCollection, final Boolean isTest) {
        Objects.requireNonNull(tClass);
        final String fileName = this.fileNameBuild(tClass.getSimpleName(), isCollection, isTest);
        log.info("Parse test class {} [{}] from resource file > {}", Boolean.TRUE.equals(isCollection) ? "collection" : "object",
                tClass.getSimpleName(), fileName);
        return new File(Paths.get(this.testResourcePath.toString(), fileName).toFile().getAbsolutePath());
    }

    private Boolean saveFileObject(final Object object, final File file) {
        try {
            this.mapper.writeValue(file, object);
            return Boolean.TRUE;
        } catch (final Exception ex) {
            log.error("Unable to write the test class resource file [{}]", ex.getMessage());
        }
        return Boolean.FALSE;
    }

    private String fileNameBuild(final String className, final Boolean isCollection, final Boolean isTest) {
        final String jnr = this.wordsJoiner;
        return className + (Boolean.TRUE.equals(isCollection) ? "s" : StringUtils.EMPTY)
                + ((this.label == null || this.label.trim().isEmpty()) ? StringUtils.EMPTY : jnr + this.label.trim())
                + (Boolean.TRUE.equals(isTest) ? this.fileExtension.fileTestSuffixExt(jnr) : this.fileExtension.fileExpectedSuffixExt(jnr));
    }

    private ObjectMapper buildObjectMapper() {
        return switch (this.fileExtension) {
            case TXT, JSON -> buildJsonObjectMapper();
            default -> buildYamlObjectMapper();
        };
    }

    private static ObjectMapper buildYamlObjectMapper() {
        final YAMLFactory yamlFactory = new YAMLFactory().disable(DEFAULT_DISABLED_FEATURE);
        return new ObjectMapper(yamlFactory).findAndRegisterModules().disable(DEFAULT_DATE_SER);
    }

    private static ObjectMapper buildJsonObjectMapper() {
        final JsonFactory jsonFactory = new JsonFactory();
        return new ObjectMapper(jsonFactory).findAndRegisterModules().disable(DEFAULT_DATE_SER);
    }
}


