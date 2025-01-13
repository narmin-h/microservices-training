package az.kb.training.microservices.customer.onboarding.model.entity;

import az.kb.training.microservices.customer.onboarding.enums.OnboardingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER_ONBOARDING")
@Getter
@Setter
public class CustomerOnboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private OnboardingStatus status;
}

