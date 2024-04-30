package imd.eventhub.service.CreditCard;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.model.CreditCard;
import imd.eventhub.model.Participant;
import imd.eventhub.repository.ICreditCardRepository;
import imd.eventhub.service.Participant.IParticipantService;

@Component
public class CreditCardService implements ICreditCardService{

    @Autowired
    ICreditCardRepository creditCardRepository;

    @Autowired
    IParticipantService participantService;

    @Override
    public void save(CreditCard creditCard) throws Exception{
        if(isValid(creditCard)){
            Optional<CreditCard> creditCardCandidate = creditCardRepository.findCreditCardByParticipant(creditCard.getParticipant());
            if(creditCardCandidate.isPresent()) throw new Exception("Você já tem um cartão de crédito cadastrado");
            Optional<Participant> participant = participantService.getById(2);
            System.out.println(participant.get().getUser());
            creditCard.setParticipant(participant.get());
            participant.get().setCreditCard(creditCard);
            creditCardRepository.save(creditCard);
        }
    }

    @Override
    public void delete(CreditCard creditCard) throws Exception{
        if(isValid(creditCard)){
            Optional<Participant> participant = participantService.getById(creditCard.getParticipant().getId());
            participant.get().setCreditCard(null);
            participantService.save(participant.get());
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

    @Override
    public Optional<CreditCard> getByParticipant(Participant participant) throws Exception{
        if(participant == null) throw new Exception("Participante passado é nulo");
        Optional<CreditCard> creditCard = creditCardRepository.findCreditCardByParticipant(participant);
        if(!creditCard.isPresent()) throw new Exception("Não existe um cartão de crédito vinculado ao participante informado");
        return creditCard;
    }
}