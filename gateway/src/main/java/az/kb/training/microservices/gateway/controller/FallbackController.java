package az.kb.training.microservices.gateway.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import az.kb.training.microservices.gateway.model.ErrorResponse;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadConfigurationBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/fallback")
@RestController
public class FallbackController {

    @GetMapping
    public Mono<ErrorResponse> fallback() {
        var response = new ErrorResponse(INTERNAL_SERVER_ERROR.value(),
                "Fallback says - > Can not process request!", INTERNAL_SERVER_ERROR);
        return Mono.just(response);
    }

}
