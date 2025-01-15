package az.kb.training.microservices.account.creation.component;

import az.kb.training.microservices.account.creation.model.event.AccountCreatedEvent;
import az.kb.training.microservices.account.creation.model.event.AccountCreationFailedEvent;
import az.kb.training.microservices.account.creation.model.event.CustomerCreatedEvent;
import az.kb.training.microservices.account.creation.model.event.CustomerValidatedEvent;
import az.kb.training.microservices.account.creation.service.AccountCreationService;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "customer-validation-topic")
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final AccountCreationService accountCreationService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaHandler
    public void subscribe(@Payload CustomerValidatedEvent event) {
        log.info("Consumed customer validated event,  message : {}", event.getFullName());
        try {
            if (accountCreationService.validateAccount()) {
                /*Account Saved Simulation*/
                var accountCreatedEvent = new AccountCreatedEvent(event.getId(), event.getFullName());

                CompletableFuture<SendResult<String, Object>> future =
                        kafkaTemplate.send("customer-onboarding-topic", accountCreatedEvent);

                future.whenComplete(((eventResult, throwable) -> {
                    if (Objects.isNull(throwable)) {
                        log.info("Finished producing event, event topic {}", eventResult.getRecordMetadata());
                    } else {
                        log.debug("Error happened {}", throwable.getMessage());
                    }
                }));
            } else {
                throw new IllegalArgumentException("Account creation failed");
            }
        } catch (Exception ex) {
            log.info("Exception happened , message : {}", ex.getMessage());
//            var accountCreationFailedEvent = new AccountCreationFailedEvent(event.getId(),
//                    event.getFullName(), "Account creation failed");
//            CompletableFuture<SendResult<String, Object>> future =
//                    kafkaTemplate.send("customer-cretion-topic", accountCreationFailedEvent);
//
//            future.whenComplete(((eventResult, throwable) -> {
//                if (Objects.isNull(throwable)) {
//                    log.info("Finished producing account creation failed event, event topic {}",
//                            eventResult.getRecordMetadata());
//                } else {
//                    log.debug("Error happened {}", throwable.getMessage());
//                }
//            }));

        }
    }
}
