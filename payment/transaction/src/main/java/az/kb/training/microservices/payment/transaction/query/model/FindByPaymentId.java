package az.kb.training.microservices.payment.transaction.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindByPaymentId {

    private Long paymentId;
}
