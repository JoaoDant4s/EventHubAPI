package imd.eventhub.service.CreditCard;

import java.util.Optional;

import org.springframework.stereotype.Service;

import imd.eventhub.model.CreditCard;

@Service
public interface ICreditCardService {
    Boolean isValid(CreditCard creditCard) throws Exception;
    void save(CreditCard creditCard) throws Exception;
    void delete(CreditCard creditCard) throws Exception;
    Optional<CreditCard> getByID(Integer id) throws Exception;
}
