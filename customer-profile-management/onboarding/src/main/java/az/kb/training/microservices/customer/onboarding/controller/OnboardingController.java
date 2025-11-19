package az.kb.training.microservices.customer.onboarding.controller;

import az.kb.training.microservices.customer.onboarding.model.command.CreateCustomerCommand;
import az.kb.training.microservices.customer.onboarding.service.CustomerOnboardingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/onboarding/customers")
public class OnboardingController {
    private final CustomerOnboardingService customerOnboardingService;

    @PostMapping("/customer")
    public ResponseEntity<Void> createCustomer(@RequestBody CreateCustomerCommand createCustomerCommand) {
        log.info("Received request to create customer: {}", createCustomerCommand);
        customerOnboardingService.createCustomer(createCustomerCommand);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/customer")
    public ResponseEntity<Void> test() {
        return ResponseEntity.accepted().build();
    }
}
