package az.kb.training.microservices.payment.transaction.query.handler;

import az.kb.training.microservices.payment.transaction.query.dto.TransactionView;
import az.kb.training.microservices.payment.transaction.query.model.FindByPaymentId;
import az.kb.training.microservices.payment.transaction.query.repository.TransactionReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionQueryHandler {

    private final TransactionReadRepository transactionReadRepository;

    @QueryHandler
    public TransactionView findByPaymentId(FindByPaymentId findByPaymentId) {
        log.info("Query handler -> findByPaymentId : {} " , findByPaymentId);
        var transaction = transactionReadRepository.findById(findByPaymentId.getPaymentId())
                .orElse(null);
        var transactionView = new TransactionView(transaction.getPaymentId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getStatus());
        return transactionView;
    }

}
