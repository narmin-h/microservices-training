package az.kb.training.microservices.customer.kyc.handler;

import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_ONBOARDING_TOPIC;
import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_VALIDATION_TOPIC;

import az.kb.training.microservices.customer.customercore.component.KafkaPublisher;
import az.kb.training.microservices.customer.customercore.constant.KafkaConstants;
import az.kb.training.microservices.customer.customercore.model.event.AccountCreationFailedEvent;
import az.kb.training.microservices.customer.customercore.model.event.CustomerCreatedEvent;
import az.kb.training.microservices.customer.customercore.model.event.CustomerValidationFailedEvent;
import az.kb.training.microservices.customer.kyc.event.CustomerValidatedEvent;
import az.kb.training.microservices.customer.kyc.service.KycService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = KafkaConstants.CUSTOMER_CREATION_TOPIC,
        groupId = KafkaConstants.CUSTOMER_CREATION_TOPIC_GROUP_ID,
        containerFactory = KafkaConstants.CUSTOMER_CREATION_TOPIC_CONTAINER_FACTORY)
@Slf4j
@RequiredArgsConstructor
public class KycHandler {

    private final KycService kycService;
    private final KafkaPublisher kafkaPublisher;

    @KafkaHandler
    public void subscribe(@Payload CustomerCreatedEvent event) {

        log.info("Consumed message : {}", event.getFullName());

        try {
            if (kycService.customerValidationEnabled) {
                var customerValidationEvent = new CustomerValidatedEvent(event.getId(), event.getFullName());
                kafkaPublisher.publishMessage(CUSTOMER_VALIDATION_TOPIC, customerValidationEvent);
            } else {
                log.info("Throwing exception...");
                throw new IllegalArgumentException("Validation failed...");
            }
        } catch (Exception ex) {
            log.info("Exception happened , message : {}", ex.getMessage());
            var onboardingFailedEvent = new CustomerValidationFailedEvent();
            onboardingFailedEvent.setId(event.getId());
            onboardingFailedEvent.setMessage("Validation failed");
            kafkaPublisher.publishMessage(CUSTOMER_ONBOARDING_TOPIC, onboardingFailedEvent,
                    KafkaConstants.VALIDATION_FAILURE_EVENT);
        }
    }


/*    @KafkaHandler
    public void subscribe(@Payload AccountCreationFailedEvent event) {
        log.info("Consumed account creation failed event, message : {}", event.getFullName());
        *//** Simulation for rollback**//*

        var onboardingFailedEvent = new CustomerValidationFailedEvent();
        onboardingFailedEvent.setId(event.getId());
        onboardingFailedEvent.setMessage("Validation failed");
        kafkaPublisher.publishMessage(CUSTOMER_ONBOARDING_TOPIC, onboardingFailedEvent,
                KafkaConstants.ACCOUNT_CREATION_FAILED_EVENT);
    }*/
}

