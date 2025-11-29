package org.DigiCorp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 * Jackson Object Mapper Context Resolver
 * our purpose of using this class is to control the formatted
 * serializing/deserializing of our LocalDate objects
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    /**
     * Jackson ObjectMapper used for JSON processing
     */
    private final ObjectMapper mapper;

    /**
     * constructor configures the ObjectMapper
     */
    public ObjectMapperContextResolver() {
        mapper = new ObjectMapper();
        // helps format the LocalDate for proper serialization
        // and forces the date format to become yyyy-mm-dd
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * returns configured ObjectMapper instance
     *
     * @param type class being serialized
     * @return configured ObjectMapper instance
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
