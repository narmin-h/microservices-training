package az.kb.training.microservices.gateway.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {

    public CustomGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("Custom Header Name = {}, Custom Header Value = {}",
                    config.getHeaderName(), config.getHeaderValue());
            var request =
                    exchange.getRequest().mutate().header(config.getHeaderName(), config.getHeaderValue()).build();
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    @Data
    public static class Config {
        private String headerName;
        private String headerValue;
        private boolean enabled;
    }
}
