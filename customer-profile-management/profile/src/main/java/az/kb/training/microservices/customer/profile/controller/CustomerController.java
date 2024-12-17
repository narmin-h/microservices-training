package az.kb.training.microservices.customer.profile.controller;

import az.kb.training.microservices.customer.profile.model.response.CustomerResponse;
import az.kb.training.microservices.customer.profile.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;



    @GetMapping("/profile/{id}")
    public CustomerResponse findById(@PathVariable Long id) {
        return customerService.findById(id);
    }
}