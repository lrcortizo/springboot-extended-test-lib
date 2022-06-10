package com.lrcortizo.springframework.boot.configuration;

import com.lrcortizo.springframework.boot.service.MockService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MockService.class, TestContext.class})
public class ExtendedTestAutoConfiguration {
}
