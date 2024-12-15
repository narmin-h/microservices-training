package az.kb.training.microservices.gateway.model;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int code, String messgae, HttpStatus httpStatus) {
}
