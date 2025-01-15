package az.kb.training.microservices.account.creation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCreationService {

    @Value("${application.account.creation.enabled}")
    private boolean accountCreationEnable;

    public boolean validateAccount() {
        return accountCreationEnable;
    }
}
