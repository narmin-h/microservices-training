package az.kb.training.microservices.payment.processing.command.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentCommand {

    @TargetAggregateIdentifier
    private Long paymentId;
    private Long accountId;
    private BigDecimal amount;
    private String currency;
}
