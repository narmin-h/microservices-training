package az.kb.training.microservices.payment.transaction.query.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionView {

    private Long paymentId;
    private Long accountId;
    private BigDecimal amount;
    private String currency;
    private String status;
}
