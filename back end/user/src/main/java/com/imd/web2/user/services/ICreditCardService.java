package com.imd.web2.user.services;

import java.util.Optional;
import org.springframework.stereotype.Service;

import imd.eventhub.model.CreditCard;
import imd.eventhub.model.Participant;

@Service
public interface ICreditCardService {
    public Optional<CreditCardDTO> getById(Integer id);
    Boolean isValid(CreditCard creditCard) throws Exception;
    public CreditCardDTO save(SaveCreditCardDTO creditCard) throws Exception;
    public CreditCardDTO update(UpdateCreditCardDTO creditCard) throws Exception;
    void delete(Integer id) throws Exception;
    Optional<CreditCardDTO> getByParticipant(Participant participant) throws Exception;
}
