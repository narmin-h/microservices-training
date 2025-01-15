package az.kb.training.microservices.account.creation.model.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerValidatedEvent {

    private Long id;
    private String fullName;
}
