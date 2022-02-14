package com.lrcortizo.springframework.boot.mapper.objectmapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class JsonMapperBuilderService implements ObjectMapperBuilderService {

    @Override
    public ObjectMapper buildObjectMapper() {
        final JsonFactory jsonFactory = new JsonFactory();
        return new ObjectMapper(jsonFactory).findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
