package az.kb.training.microservices.customer.onboarding.repository;

import az.kb.training.microservices.customer.onboarding.model.entity.CustomerOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOnboardingRepository  extends JpaRepository<CustomerOnboarding, Long> {
}
