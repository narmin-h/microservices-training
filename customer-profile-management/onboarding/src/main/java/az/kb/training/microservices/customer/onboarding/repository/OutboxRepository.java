package az.kb.training.microservices.customer.onboarding.repository;

import az.kb.training.microservices.customer.onboarding.model.entity.CustomerOnboarding;
import az.kb.training.microservices.customer.onboarding.model.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
}
