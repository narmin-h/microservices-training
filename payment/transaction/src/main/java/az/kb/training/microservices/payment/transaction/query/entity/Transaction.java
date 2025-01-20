package az.kb.training.microservices.payment.transaction.query.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TRANSACTION")
@Getter
@Setter
public class Transaction {

    @Id
    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "STATUS")
    private String status;
}
