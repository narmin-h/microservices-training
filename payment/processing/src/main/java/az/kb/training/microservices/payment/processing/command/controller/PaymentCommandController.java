package az.kb.training.microservices.payment.processing.command.controller;

import az.kb.training.microservices.payment.processing.command.model.CreatePaymentCommand;
import az.kb.training.microservices.payment.processing.command.model.request.CreatePaymentRequest;
import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/payments")
@RestController
@RequiredArgsConstructor
public class PaymentCommandController {

    private final CommandGateway commandGateway;

    @PostMapping
    public CompletableFuture<Long> createPaymentCommand(@RequestBody  CreatePaymentRequest createPaymentRequest) {
        var paymentId = new SecureRandom().nextLong(100L);

        return commandGateway.send(new CreatePaymentCommand(paymentId,
                createPaymentRequest.getAccountId(),
                createPaymentRequest.getAmount(),
                createPaymentRequest.getCurrency()));
    }
}
