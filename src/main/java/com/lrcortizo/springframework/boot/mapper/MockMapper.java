package com.lrcortizo.springframework.boot.mapper;

import lombok.Setter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MockMapper.TestConfiguration.class})
@Component
public class MockMapper {

    @Autowired
    private TestClassMapperProperties properties;

    @EnableConfigurationProperties(TestClassMapperProperties.class)
    public static class TestConfiguration {
    }

    @Setter
    private String label = "";

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
        return new TestClassMapper(this.label, this.properties)
                .loadExpectedClassCollection(type, TestClassMapper.FileExtension.JSON);
    }

    public <T> T loadTestExpectedClass(final Class<T> type) throws IOException {
        return new TestClassMapper(this.label, this.properties).loadTestExpectedClass(type);
    }

    public <T> T loadJsonTestExpectedClass(final Class<T> type) throws IOException {
        return new TestClassMapper(this.label, this.properties).loadTestExpectedClass(type,
                TestClassMapper.FileExtension.JSON);
    }

    public <T> Boolean saveFileObject(final Object object, final Class<T> type, final Boolean isTest) {
        return new TestClassMapper(this.label, this.properties).saveFileObject(object, type, isTest);
    }
}
