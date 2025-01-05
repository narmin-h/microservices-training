package az.kb.training.microservices.account.creation.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreatedEvent {
    private String firstName;
    private String lastName;
    private String email;
}
