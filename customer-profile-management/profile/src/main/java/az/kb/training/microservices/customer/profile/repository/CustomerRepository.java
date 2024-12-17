package az.kb.training.microservices.customer.profile.repository;

import az.kb.training.microservices.customer.profile.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
