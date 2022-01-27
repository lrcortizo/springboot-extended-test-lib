package com.lrcortizo.springframework.boot.mapper;

import com.lrcortizo.springframework.boot.configuration.TestClassMapperConfiguration;
import com.lrcortizo.springframework.boot.configuration.properties.TestClassMapperProperties;
import com.lrcortizo.springframework.boot.model.catalog.FileExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestClassMapperConfiguration.class})
@Component
public class MockMapper {

    private final TestClassMapperProperties testClassMapperProperties;

    public MockMapper(final TestClassMapperProperties testClassMapperProperties) {
        this.testClassMapperProperties = testClassMapperProperties;
    }

    public <T> T loadJsonTestClass(final Class<T> type, final String label) throws IOException {
        return new TestClassMapper(this.testClassMapperProperties).loadTestClass(type, label);
    }

    public <T> T loadYamlTestClass(final Class<T> type, final String label) throws IOException {
        return new TestClassMapper(this.testClassMapperProperties).loadTestClass(type, label, FileExtension.YAML);
    }

    public <T> Collection<T> loadJsonTestClassCollection(final Class<T> type, final String label) throws IOException {
        return new TestClassMapper(this.testClassMapperProperties).loadTestClassCollection(type, label);
    }

    public <T> Collection<T> loadYamlTestClassCollection(final Class<T> type, final String label) throws IOException {
        return new TestClassMapper(this.testClassMapperProperties)
                .loadTestClassCollection(type, label, FileExtension.YAML);
    }

    public <T> Boolean saveFileObject(final Object object, final Class<T> type, final String label) {
        return new TestClassMapper(this.testClassMapperProperties).saveFileObject(object, label, type);
    }
}
