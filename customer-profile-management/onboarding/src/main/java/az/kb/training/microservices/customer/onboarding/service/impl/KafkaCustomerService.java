package az.kb.training.microservices.customer.onboarding.service.impl;


import az.kb.training.microservices.customer.onboarding.model.command.CreateCustomerCommand;
import az.kb.training.microservices.customer.onboarding.model.event.CustomerCreatedEvent;
import az.kb.training.microservices.customer.onboarding.service.CustomerOnboardingService;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Primary
public class KafkaCustomerService implements CustomerOnboardingService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void createCustomer(CreateCustomerCommand command) {
        var customerCreatedEvent =
                new CustomerCreatedEvent(command.getFirstName(), command.getLastName(), command.getEmail());
        CompletableFuture<SendResult<String, Object>>  future =
                kafkaTemplate.send("customer-cretion-topic", command.getFirstName(), customerCreatedEvent);
        future.whenComplete(((eventResult, throwable) -> {
            if(Objects.isNull(throwable)) {
                log.info("Finished producing,  name {}", eventResult.getRecordMetadata());
            }else {
                log.debug("Error happened {}", throwable.getMessage());
            }
        }) );
    }
}
