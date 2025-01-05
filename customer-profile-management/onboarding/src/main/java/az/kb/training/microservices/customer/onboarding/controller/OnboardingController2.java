package az.kb.training.microservices.customer.onboarding.controller;

import az.kb.training.microservices.customer.onboarding.model.command.CreateCustomerCommand;
import az.kb.training.microservices.customer.onboarding.service.CustomerOnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/onboarding")
@RequiredArgsConstructor
public class OnboardingController2 {


    @GetMapping("/customer")
    public ResponseEntity<Void> test() {
        return ResponseEntity.accepted().build();
    }
}
