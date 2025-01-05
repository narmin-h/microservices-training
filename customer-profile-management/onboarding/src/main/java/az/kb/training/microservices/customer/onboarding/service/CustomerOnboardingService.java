package az.kb.training.microservices.customer.onboarding.service;


import az.kb.training.microservices.customer.onboarding.model.command.CreateCustomerCommand;

public interface CustomerOnboardingService {
     void createCustomer(CreateCustomerCommand command);
}
