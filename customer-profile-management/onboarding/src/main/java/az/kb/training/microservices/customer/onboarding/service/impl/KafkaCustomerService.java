package az.kb.training.microservices.customer.onboarding.service.impl;


import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_CREATION_TOPIC;
import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_VALIDATION_TOPIC;

import az.kb.training.microservices.customer.customercore.component.KafkaPublisher;
import az.kb.training.microservices.customer.customercore.constant.KafkaConstants;
import az.kb.training.microservices.customer.customercore.exception.NotFoundException;
import az.kb.training.microservices.customer.customercore.model.event.CustomerCreatedEvent;
import az.kb.training.microservices.customer.onboarding.enums.OnboardingStatus;
import az.kb.training.microservices.customer.onboarding.model.command.CreateCustomerCommand;
import az.kb.training.microservices.customer.onboarding.model.entity.CustomerOnboarding;
import az.kb.training.microservices.customer.onboarding.model.entity.Outbox;
import az.kb.training.microservices.customer.onboarding.repository.CustomerOnboardingRepository;
import az.kb.training.microservices.customer.onboarding.repository.OutboxRepository;
import az.kb.training.microservices.customer.onboarding.service.CustomerOnboardingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Primary
public class KafkaCustomerService implements CustomerOnboardingService {
    private final CustomerOnboardingRepository customerOnboardingRepository;
    private final OutboxRepository outboxRepository;

    private final KafkaPublisher kafkaPublisher;
    private final ObjectMapper objectMapper;


    @Transactional
    @Override
    public void createCustomer(CreateCustomerCommand command) throws JsonProcessingException {
        var onboardingCustomer = new CustomerOnboarding();
        var fullName = command.getFirstName() + "" + command.getLastName();
        onboardingCustomer.setName(fullName);
        onboardingCustomer.setEmail(command.getEmail());
        onboardingCustomer.setStatus(OnboardingStatus.PENDING);

        onboardingCustomer = customerOnboardingRepository.save(onboardingCustomer);

        var id = new SecureRandom().nextLong(1000L);
        var customerCreatedEvent =
                new CustomerCreatedEvent(id, fullName);
        var outbox = new Outbox();
        outbox.setPayload(objectMapper.writeValueAsString(customerCreatedEvent));
        outbox.setDestination("validation");
        outboxRepository.save(outbox);
        //kafkaPublisher.publishMessage(CUSTOMER_CREATION_TOPIC, customerCreatedEvent);
    }

    @Override
    @Transactional
    public void updateCustomer(Long id, OnboardingStatus status) {
        var onboardingCustomer =
                customerOnboardingRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found customer"));
        onboardingCustomer.setStatus(status);
    }
}
