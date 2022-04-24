package com.lrcortizo.springframework.boot.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final String DEFAULT_RESOURCES_PATH = "src\\test\\resources\\mocks";
    private static final String DEFAULT_WORDS_JOINER = "_";
    private static final String DEFAULT_COLLECTIONS_LABEL = "s";

    private final Path mocksPath;
    private final String wordsJoiner;
    private final String collectionsLabel;
    private final ObjectMapper objectMapper;

    TestClassMapper(@NonNull final TestClassMapperProperties properties, @NonNull final ObjectMapper objectMapper) {
        this.mocksPath = Paths.get(Optional.ofNullable(properties.getMocksPath())
                .orElse(DEFAULT_RESOURCES_PATH));
        this.wordsJoiner = Optional.ofNullable(properties.getWordsJoiner()).orElse(DEFAULT_WORDS_JOINER);
        this.collectionsLabel = Optional.ofNullable(properties.getCollectionsLabel()).orElse(DEFAULT_COLLECTIONS_LABEL);
        this.objectMapper = objectMapper;
    }

    public <T> T loadTestClass(final Class<T> type, final String label)
            throws IOException {
        return this.objectMapper.readValue(this.loadFile(type, label, Boolean.FALSE), type);
    }

    public <T> Collection<T> loadTestClassCollection(final Class<T> type, final String label) throws IOException {
        return this.objectMapper.readValue(this.loadFile(type, label, Boolean.TRUE), this.objectMapper.getTypeFactory()
                .constructCollectionType(Collection.class, type));
    }

    public <T> void saveFileObject(final Object object, final Class<T> type, final String label) {
        Objects.requireNonNull(object);
        final File file = this.loadFile(type, label, (object instanceof Collection<?>));
        log.info("Write test class resource file [{}] from collection > {}", file.getName(), type.getSimpleName());
        this.saveFileObject(object, file);
    }

    private <T> File loadFile(final Class<T> tClass, final String label, final boolean isCollection) {
        Objects.requireNonNull(tClass);
        final String fileName = this.fileNameBuild(tClass.getSimpleName(), label, isCollection);
        log.info("Load test class {} [{}] from resource file > {}",
                isCollection ? "collection" : "object", tClass.getSimpleName(), fileName);
        return new File(Paths.get(this.mocksPath.toString(), fileName).toFile().getAbsolutePath());
    }

    private void saveFileObject(final Object object, final File file) {
        try {
            this.objectMapper.writeValue(file, object);
        } catch (final IOException ex) {
            log.error("Unable to write the test class resource file [{}]", ex.getMessage(), ex);
        }
    }

    private String fileNameBuild(final String className, final String label, final boolean isCollection) {
        return className + (isCollection ? this.collectionsLabel : StringUtils.EMPTY)
                + (StringUtils.isEmpty(label.trim()) ? StringUtils.EMPTY : this.wordsJoiner + label.trim());
    }
}
