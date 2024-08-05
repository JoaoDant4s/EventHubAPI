package imd.eventhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.CreditCard;
import imd.eventhub.model.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICreditCardRepository extends JpaRepository<CreditCard, Integer>{
    Optional<CreditCard> findCreditCardByParticipant(Participant participant);

    @Query(value = " select c from CreditCard c where c.participant.id = :id ")
    Optional<CreditCard> getByParticipantId(@Param("id") Integer id);
}
