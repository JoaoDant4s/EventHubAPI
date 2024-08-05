package imd.eventhub.service.CreditCard;

import java.util.Optional;

import imd.eventhub.exception.CreditCardNotValidException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.restAPI.dto.creditCard.CreditCardDTO;
import imd.eventhub.restAPI.dto.creditCard.SaveCreditCardDTO;
import imd.eventhub.restAPI.dto.creditCard.UpdateCreditCardDTO;
import imd.eventhub.restAPI.dto.feedback.FeedbackDTO;
import org.springframework.stereotype.Service;

import imd.eventhub.model.CreditCard;
import imd.eventhub.model.Participant;

@Service
public interface ICreditCardService {
    public Optional<CreditCardDTO> getById(Integer id);
    Boolean isValid(CreditCard creditCard) throws Exception;
    public CreditCardDTO save(SaveCreditCardDTO creditCard) throws NotFoundException, CreditCardNotValidException;
    public CreditCardDTO update(UpdateCreditCardDTO creditCard) throws Exception;
    void delete(Integer id) throws Exception;
    Optional<CreditCardDTO> getByParticipantId(Integer id) throws Exception;
}
