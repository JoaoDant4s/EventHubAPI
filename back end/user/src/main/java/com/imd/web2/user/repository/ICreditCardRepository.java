package com.imd.web2.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.user.model.CreditCard;
import com.imd.web2.user.model.Participant;

public interface ICreditCardRepository extends JpaRepository<CreditCard, Integer>{
    Optional<CreditCard> findCreditCardByParticipant(Participant participant);
}