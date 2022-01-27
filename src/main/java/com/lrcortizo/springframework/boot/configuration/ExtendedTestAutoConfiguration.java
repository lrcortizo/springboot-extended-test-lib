package com.lrcortizo.springframework.boot.configuration;

import com.lrcortizo.springframework.boot.mock.MockCollection;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MockCollection.class, TestContext.class})
public class ExtendedTestAutoConfiguration {
}
