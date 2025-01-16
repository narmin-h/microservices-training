package az.kb.training.microservices.payment.processing.command.model.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
    private Long accountId;
    private BigDecimal amount;
    private String currency;
}
