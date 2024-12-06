package com.imd.web2.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.user.model.CreditCard;
import com.imd.web2.user.model.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICreditCardRepository extends JpaRepository<CreditCard, Integer>{
    Optional<CreditCard> findCreditCardByParticipant(Participant participant);

    @Query(value = " select c from CreditCard c where c.participant.id = :id ")
    Optional<CreditCard> getByParticipantId(@Param("id") Integer id);
}