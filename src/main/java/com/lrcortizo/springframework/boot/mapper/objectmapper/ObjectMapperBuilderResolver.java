package com.lrcortizo.springframework.boot.mapper.objectmapper;

import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ObjectMapperBuilderResolver {

    private final Map<String, ObjectMapperBuilderService> objectMapperBuilderServices;

    public ObjectMapperBuilderResolver(final List<ObjectMapperBuilderService> objectMapperBuilders) {
        this.objectMapperBuilderServices = objectMapperBuilders.stream()
                .collect(Collectors.toUnmodifiableMap(
                        service -> ClassUtils.getUserClass(service.getClass()).getSimpleName(),
                        service -> service));
    }

    public ObjectMapperBuilderService resolve(final String resolverKey) {
        return this.objectMapperBuilderServices.get(resolverKey);
    }
}
