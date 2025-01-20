package az.kb.training.microservices.payment.transaction.query.projection;

import az.kb.training.microservices.payment.shared.model.event.PaymentCreatedEvent;
import az.kb.training.microservices.payment.transaction.query.entity.Transaction;
import az.kb.training.microservices.payment.transaction.query.repository.TransactionReadRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionProjection {
    private final TransactionReadRepository transactionReadRepository;


    @EventHandler
    public void on(PaymentCreatedEvent paymentCreatedEvent) {
        var transaction = new Transaction();
        transaction.setPaymentId(paymentCreatedEvent.getPaymentId());
        transaction.setAccountId(paymentCreatedEvent.getAccountId());
        transaction.setAmount(paymentCreatedEvent.getAmount());
        transaction.setCurrency(paymentCreatedEvent.getCurrency());
        transaction.setStatus(paymentCreatedEvent.getStatus());
        transactionReadRepository.save(transaction);
    }
}
