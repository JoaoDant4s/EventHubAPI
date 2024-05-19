package imd.eventhub.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import imd.eventhub.model.Payment;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, UUID> {
}
