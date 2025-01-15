package az.kb.training.microservices.customer.customercore.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerValidationFailedEvent {
    private Long id;
    private String fullName;
    private String message;
}
