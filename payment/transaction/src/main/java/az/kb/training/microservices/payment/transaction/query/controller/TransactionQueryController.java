package az.kb.training.microservices.payment.transaction.query.controller;
import az.kb.training.microservices.payment.transaction.query.dto.TransactionView;
import az.kb.training.microservices.payment.transaction.query.model.FindByPaymentId;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/transactions/query")
@RestController
@RequiredArgsConstructor
public class TransactionQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("/{paymentId}")
    public TransactionView createPaymentCommand(@PathVariable Long paymentId) {
        var findByPaymentId = new FindByPaymentId(paymentId);
        return queryGateway.query(findByPaymentId, ResponseTypes.instanceOf(TransactionView.class)).join();
    }
}
