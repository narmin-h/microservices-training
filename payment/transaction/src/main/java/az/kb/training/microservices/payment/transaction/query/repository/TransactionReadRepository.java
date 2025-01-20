package az.kb.training.microservices.payment.transaction.query.repository;

import az.kb.training.microservices.payment.transaction.query.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionReadRepository extends JpaRepository<Transaction, Long> {
}
