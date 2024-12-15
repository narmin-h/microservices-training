package az.kb.training.microservices.gateway.config;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import az.kb.training.microservices.gateway.model.ErrorResponse;
import az.kb.training.microservices.gateway.util.JsonHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Configuration
//@Slf4j
//public class CustomFailedRequestPostFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        return chain.filter(exchange)
//                .doOnError((e) -> {
//                    log.info("Service not available!", e);
//                }).onErrorResume(e -> {
//                    var responseMessage = new ErrorResponse(INTERNAL_SERVER_ERROR.value(),
//                            "Can not process request!", INTERNAL_SERVER_ERROR);
//                    var response = exchange.getResponse();
//                    var bufferFactory = response.bufferFactory().wrap(JsonHandler.toBytes(responseMessage));
//                    return response.writeWith(Mono.just(bufferFactory));
//                });
//    }
//
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
//    }
//}