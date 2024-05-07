package imd.eventhub.service.CreditCard;

import java.util.Optional;
import java.util.regex.Pattern;

import imd.eventhub.exception.CreditCardNotValidException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.repository.IParticipantRepository;
import imd.eventhub.restAPI.dto.creditCard.CreditCardDTO;
import imd.eventhub.restAPI.dto.creditCard.SaveCreditCardDTO;
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

    @Autowired
    IParticipantRepository participantRepository;

    @Override
    public CreditCardDTO save(SaveCreditCardDTO creditCardDTO) throws Exception{

        if (creditCardDTO.getCardNumber() == null || creditCardDTO.getCardNumber().isEmpty()) throw new NotFoundException("Número do cartão não pode estar vazio");
        if (creditCardDTO.getExpiration() == null) throw new NotFoundException("Data de validade não pode estar vazia");
        if (creditCardDTO.getCardHolderName() == null || creditCardDTO.getCardHolderName().isEmpty()) throw new NotFoundException("Nome do titular não pode estar vazio");
        if (!checkCardNumberIsValid(creditCardDTO.getCardNumber())) throw new CreditCardNotValidException("O número do cartão de crédito precisar seguir a seguinte formatação: ____ ____ ____ ____");
        Optional<Participant> participant = participantRepository.findById(creditCardDTO.getParticipantId());
        if(participant.isEmpty()) throw new NotFoundException("Participante não encontrado");
        Optional<CreditCard> creditCardCandidate = creditCardRepository.findCreditCardByParticipant(participant.get());
        if(creditCardCandidate.isPresent()) throw new CreditCardNotValidException("Você já tem um cartão de crédito cadastrado");

        CreditCard creditCard = new CreditCard();
        creditCard.setCardHolderName(creditCardDTO.getCardHolderName());
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setExpiration(creditCardDTO.getExpiration());
        creditCard.setParticipant(participant.get());

        CreditCard savedCreditCard = creditCardRepository.save(creditCard);

        CreditCardDTO showCreditCard = CreditCardDTO.convertCreditCardToCreditCardDTO(savedCreditCard);

        return showCreditCard;
    }

    @Override
    public void delete(Integer id) throws Exception{
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        if(creditCard.isEmpty()) throw new NotFoundException("Cartão de crédito não encontrado");
        Participant participant = creditCard.get().getParticipant();
        participant.setCreditCard(null);
        participantRepository.save(participant);
        creditCardRepository.delete(creditCard.get());
    }

    @Override
    public Optional<CreditCardDTO> getById(Integer id){
        Optional<CreditCardDTO> creditCardDTO = creditCardRepository.findById(id).stream().findAny().map(CreditCardDTO::convertCreditCardToCreditCardDTO);
        if(creditCardDTO.isEmpty()) throw new NotFoundException("Cartão de crédito não encontrado");
        return creditCardDTO;
    }
    @Override
    public Boolean isValid(CreditCard creditCard) throws Exception {
        if (creditCard.getCardNumber() == null || creditCard.getCardNumber().isEmpty()) throw new NotFoundException("Número do cartão não pode estar vazio");
        if (creditCard.getExpiration() == null) throw new NotFoundException("Data de validade não pode estar vazia");
        if (creditCard.getCardHolderName() == null || creditCard.getCardHolderName().isEmpty()) throw new NotFoundException("Nome do titular não pode estar vazio");
        return true;
    }

    @Override
    public Optional<CreditCardDTO> getByParticipant(Participant participant) throws Exception{
        if(participant == null) throw new Exception("Participante passado é nulo");
        Optional<CreditCardDTO> creditCardDTO = creditCardRepository.findCreditCardByParticipant(participant).stream().findAny().map(CreditCardDTO::convertCreditCardToCreditCardDTO);
        if(creditCardDTO.isEmpty()) throw new Exception("Não existe um cartão de crédito vinculado ao participante informado");
        return creditCardDTO;
    }

    public static boolean checkCardNumberIsValid(String cardNumber){
        Pattern regex = Pattern.compile("[0-9]{4}[ ][0-9]{4}[ ][0-9]{4}[ ][0-9]{4}");
        return regex.matcher(cardNumber).find();
    }
}