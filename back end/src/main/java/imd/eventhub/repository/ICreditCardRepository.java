package imd.eventhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.CreditCard;
import imd.eventhub.model.Participant;

public interface ICreditCardRepository extends JpaRepository<CreditCard, Integer>{
    Optional<CreditCard> findCreditCardByParticipant(Participant participant);
}
