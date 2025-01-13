package az.kb.training.microservices.customer.kyc.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationFailedEvent {

    private Long id;
    private String fullName;
    private String message; ;
}
