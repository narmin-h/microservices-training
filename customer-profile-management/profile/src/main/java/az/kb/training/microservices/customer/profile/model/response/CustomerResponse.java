package az.kb.training.microservices.customer.profile.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerResponse {

    private String name;
    private String email;
}
