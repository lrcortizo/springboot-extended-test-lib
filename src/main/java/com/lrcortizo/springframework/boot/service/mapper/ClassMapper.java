package com.lrcortizo.springframework.boot.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrcortizo.springframework.boot.configuration.properties.ClassMapperProperties;
import com.lrcortizo.springframework.boot.model.ClassMapperConstants;
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
public class ClassMapper {

    private final Path mocksPath;
    private final String wordsJoiner;
    private final String collectionsLabel;
    private final ObjectMapper objectMapper;

    ClassMapper(@NonNull final ClassMapperProperties properties, @NonNull final ObjectMapper objectMapper) {
        this.mocksPath = Paths.get(Optional.ofNullable(properties.getMocksPath())
                .orElse(ClassMapperConstants.DEFAULT_RESOURCES_PATH));
        this.wordsJoiner = Optional.ofNullable(properties.getWordsJoiner())
                .orElse(ClassMapperConstants.DEFAULT_WORDS_JOINER);
        this.collectionsLabel = Optional.ofNullable(properties.getCollectionsLabel())
                .orElse(ClassMapperConstants.DEFAULT_COLLECTIONS_LABEL);
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

    public <T> Boolean writeFileObject(final Object object, final Class<T> type, final String label) {
        Objects.requireNonNull(object);
        final File file = this.loadFile(type, label, (object instanceof Collection<?>));
        log.info("Write test class resource file [{}] from collection > {}", file.getName(), type.getSimpleName());
        return this.writeFileObject(object, file);
    }

    private <T> File loadFile(final Class<T> tClass, final String label, final Boolean isCollection) {
        Objects.requireNonNull(tClass);
        final String fileName = this.buildFileName(tClass.getSimpleName(), label, isCollection);
        log.info("Load test class {} [{}] from resource file > {}",
                Boolean.TRUE.equals(isCollection) ? "collection" : "object", tClass.getSimpleName(), fileName);
        return new File(Paths.get(this.mocksPath.toString(), fileName).toFile().getAbsolutePath());
    }

    private Boolean writeFileObject(final Object object, final File file) {
        try {
            this.objectMapper.writeValue(file, object);
            return Boolean.TRUE;
        } catch (final Exception ex) {
            log.error("Unable to write the test class resource file [{}]", ex.getMessage());
        }
        return Boolean.FALSE;
    }

    private String buildFileName(final String className, final String label, final Boolean isCollection) {
        return className + (Boolean.TRUE.equals(isCollection) ? this.collectionsLabel : StringUtils.EMPTY)
                + (StringUtils.isEmpty(label.trim()) ? StringUtils.EMPTY : this.wordsJoiner + label.trim());
    }
}
