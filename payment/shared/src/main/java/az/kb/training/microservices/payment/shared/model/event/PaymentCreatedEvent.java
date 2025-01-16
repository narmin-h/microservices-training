package az.kb.training.microservices.payment.shared.model.event;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PaymentCreatedEvent {
    private Long paymentId;
    private Long accountId;
    private BigDecimal amount;
    private String currency;
    private String status;
}
