package az.kb.training.microservices.customer.onboarding.handler;

import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_ONBOARDING_TOPIC;

import az.kb.training.microservices.customer.customercore.constant.KafkaConstants;
import az.kb.training.microservices.customer.customercore.model.event.AccountCreatedEvent;
import az.kb.training.microservices.customer.customercore.model.event.CustomerValidationFailedEvent;
import az.kb.training.microservices.customer.onboarding.enums.OnboardingStatus;
import az.kb.training.microservices.customer.onboarding.service.CustomerOnboardingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = CUSTOMER_ONBOARDING_TOPIC,
        groupId = KafkaConstants.CUSTOMER_ONBOARDING_TOPIC_GROUP_ID,
        containerFactory = KafkaConstants.CUSTOMER_ONBOARDING_TOPIC_CONTAINER_FACTORY)
public class CustomerOnboardingHandler {

    private final CustomerOnboardingService service;


    @KafkaHandler
    public void subscribe(@Validated @Header(KafkaConstants.EVENT_TYPE) String eventType,
                          @Payload CustomerValidationFailedEvent event) {
        log.info("Consumed failure message : {}, event type {}", event.getMessage(), eventType);
        var status = KafkaConstants.VALIDATION_FAILURE_EVENT.equals(eventType) ?
                OnboardingStatus.REJECTED : OnboardingStatus.FAILED;
        service.updateCustomer(event.getId(), status);
    }

    @KafkaHandler
    public void subscribe(@Validated @Payload AccountCreatedEvent event) {
        log.info("Consumed account created event message : {}", event.getFullName());
        service.updateCustomer(event.getId(), OnboardingStatus.COMPLETED);
        log.info("Onboarding completed successfully, id :{}", event.getId());
    }
}
