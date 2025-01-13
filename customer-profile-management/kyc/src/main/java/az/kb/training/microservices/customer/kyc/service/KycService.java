package az.kb.training.microservices.customer.kyc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KycService {

    @Value("${application.customer.validation.enabled}")
    public boolean customerValidationEnabled;

    public boolean validate() {
        /* REAL WORLD SERVICE SIMULATION */
        return customerValidationEnabled;
    }
}
