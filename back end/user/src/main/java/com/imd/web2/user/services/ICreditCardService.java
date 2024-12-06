package com.imd.web2.user.services;

import java.util.Optional;

import com.imd.web2.user.resources.exceptions.CreditCardNotValidException;
import com.imd.web2.user.resources.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import com.imd.web2.user.model.CreditCard;
import com.imd.web2.user.model.Participant;
import com.imd.web2.user.resources.dto.CreditCardDTO;
import com.imd.web2.user.resources.dto.SaveCreditCardDTO;
import com.imd.web2.user.resources.dto.UpdateCreditCardDTO;



@Service
public interface ICreditCardService {
    public Optional<CreditCardDTO> getById(Integer id);
    Boolean isValid(CreditCard creditCard) throws Exception;
    public CreditCardDTO save(SaveCreditCardDTO creditCard) throws NotFoundException, CreditCardNotValidException;
    public CreditCardDTO update(UpdateCreditCardDTO creditCard) throws Exception;
    void delete(Integer id) throws Exception;
    Optional<CreditCardDTO> getByParticipantId(Integer id) throws Exception;
}
