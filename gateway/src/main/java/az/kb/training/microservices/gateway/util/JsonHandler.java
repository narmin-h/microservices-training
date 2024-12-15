package az.kb.training.microservices.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonHandler() {
    }

    public static <T> byte[] toBytes(T value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
