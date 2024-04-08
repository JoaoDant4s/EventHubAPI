package imd.eventhub.service.CreditCard;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.model.CreditCard;
import imd.eventhub.repository.ICreditCardRepository;

@Component
public class CreditCardService implements ICreditCardService{

    @Autowired
    ICreditCardRepository creditCardRepository;

    @Override
    public void save(CreditCard creditCard) throws Exception{
        if(isValid(creditCard)){
            Optional<CreditCard> creditCardCandidate = creditCardRepository.findCreditCardByParticipant(creditCard.getParticipant());
            if(creditCardCandidate.isPresent()) throw new Exception("Você já tem um cartão de crédito cadastrado");
            creditCardRepository.save(creditCard);
        }
    }

    @Override
    public void delete(CreditCard creditCard) throws Exception{
        if(isValid(creditCard)){
            creditCardRepository.delete(creditCard);
        }
    }

    @Override
    public Optional<CreditCard> getByID(Integer id) throws Exception{
        if(id == null) throw new Exception("O id passado é nulo");
        return creditCardRepository.findById(id);
    }
    @Override
    public Boolean isValid(CreditCard creditCard) throws Exception {
        if (creditCard.getCardNumber() == null || creditCard.getCardNumber().isEmpty()) throw new Exception("Número do cartão não pode estar vazio");
        if (creditCard.getExpiration() == null) throw new Exception("Data de validade não pode estar vazia");
        if (creditCard.getCardHolderName() == null || creditCard.getCardHolderName().isEmpty()) throw new Exception("Nome do titular não pode estar vazio");
        return true;
    }
}