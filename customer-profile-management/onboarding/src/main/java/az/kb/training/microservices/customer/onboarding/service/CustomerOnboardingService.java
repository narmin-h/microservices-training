package az.kb.training.microservices.customer.onboarding.service;


import az.kb.training.microservices.customer.onboarding.enums.OnboardingStatus;
import az.kb.training.microservices.customer.onboarding.model.command.CreateCustomerCommand;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CustomerOnboardingService {
     void createCustomer(CreateCustomerCommand command) throws JsonProcessingException;
     void updateCustomer(Long id, OnboardingStatus status);
}
