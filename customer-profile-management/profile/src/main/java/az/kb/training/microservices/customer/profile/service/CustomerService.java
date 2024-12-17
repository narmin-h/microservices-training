package az.kb.training.microservices.customer.profile.service;

import az.kb.training.microservices.customer.profile.model.response.CustomerResponse;
import az.kb.training.microservices.customer.profile.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse findById(Long id) {
        var customer  = customerRepository.findById(id).orElse(null);
        return new CustomerResponse(customer.getName(), customer.getEmail());
    }
}
