package az.kb.training.microservices.customer.onboarding.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerCommand {
    private String firstName;
    private String lastName;
    private String email;
}
