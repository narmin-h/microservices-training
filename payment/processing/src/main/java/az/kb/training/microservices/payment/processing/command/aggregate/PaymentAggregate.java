package az.kb.training.microservices.payment.processing.command.aggregate;

import az.kb.training.microservices.payment.processing.command.model.CreatePaymentCommand;
import az.kb.training.microservices.payment.shared.model.event.PaymentCreatedEvent;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class PaymentAggregate {

    @AggregateIdentifier
    Long paymentId;

    Long accountId;

    BigDecimal amount;

    String currency;

    String status;

    @CommandHandler
    public PaymentAggregate(CreatePaymentCommand command) {
        log.info("Command to be processed : {} ", command);
        if (command.getAmount() != null
                && BigDecimal.ZERO.compareTo(command.getAmount()) > 0) {
            throw new IllegalArgumentException();
        }

        AggregateLifecycle.apply(new
                PaymentCreatedEvent(command.getPaymentId(),
                command.getAccountId(),
                command.getAmount(),
                "CREATED", command.getCurrency()));

    }

    @EventSourcingHandler
    public void on(PaymentCreatedEvent paymentCreatedEvent) {
        log.info("Event to be processed : {} ", paymentCreatedEvent);
        this.paymentId = paymentCreatedEvent.getPaymentId();
        this.accountId = paymentCreatedEvent.getAccountId();
        this.amount = paymentCreatedEvent.getAmount();
        this.currency = paymentCreatedEvent.getCurrency();
        this.status = paymentCreatedEvent.getStatus();
    }
}
