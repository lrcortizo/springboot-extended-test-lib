package com.lrcortizo.springframework.boot.mapper;

import com.lrcortizo.springframework.boot.configuration.TestClassMapperConfiguration;
import com.lrcortizo.springframework.boot.configuration.properties.TestClassMapperProperties;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
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

    private final TestClassMapperProperties properties;

    public MockMapper(final TestClassMapperProperties properties) {
        this.properties = properties;
    }

    @Setter
    private String label = StringUtils.EMPTY;

    public <T> Collection<T> loadTestClassCollection(final Class<T> type) throws IOException {
        return new TestClassMapper(this.label, this.properties).loadTestClassCollection(type);
    }

    public <T> T loadTestClass(final Class<T> type) throws IOException {
        return new TestClassMapper(this.label, this.properties).loadTestClass(type);
    }

    public <T> Collection<T> loadTestExpectedClassCollection(final Class<T> type) throws IOException {
        return new TestClassMapper(this.label, this.properties).loadTestExpectedClassCollection(type);
    }

    public <T> Collection<T> loadJsonExpectedClassCollection(final Class<T> type) throws IOException {
        return new TestClassMapper(this.label, this.properties).loadExpectedClassCollection(type, FileExtension.JSON);
    }

    public <T> T loadTestExpectedClass(final Class<T> type) throws IOException {
        return new TestClassMapper(this.label, this.properties).loadTestExpectedClass(type);
    }

    public <T> T loadJsonTestExpectedClass(final Class<T> type) throws IOException {
        return new TestClassMapper(this.label, this.properties).loadTestExpectedClass(type, FileExtension.JSON);
    }

    public <T> Boolean saveFileObject(final Object object, final Class<T> type, final Boolean isTest) {
        return new TestClassMapper(this.label, this.properties).saveFileObject(object, type, isTest);
    }
}
