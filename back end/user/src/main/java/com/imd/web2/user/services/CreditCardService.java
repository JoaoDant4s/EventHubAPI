package com.imd.web2.user.services;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        if(creditCardCandidate.isPresent()) throw new CreditCardNotValidException("usuário já possui um cartão de crédito cadastrado");

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
    public CreditCardDTO update(UpdateCreditCardDTO creditCardDTO) throws Exception{
        Optional<CreditCard> getCreditCard = creditCardRepository.findById(creditCardDTO.getId());
        if (getCreditCard.get().getCardNumber() == null || getCreditCard.get().getCardNumber().isEmpty()) throw new NotFoundException("Número do cartão não pode estar vazio");
        if (getCreditCard.get().getExpiration() == null) throw new NotFoundException("Data de validade não pode estar vazia");
        if (getCreditCard.get().getCardHolderName() == null || getCreditCard.get().getCardHolderName().isEmpty()) throw new NotFoundException("Nome do titular não pode estar vazio");
        if (!checkCardNumberIsValid(getCreditCard.get().getCardNumber())) throw new CreditCardNotValidException("O número do cartão de crédito precisar seguir a seguinte formatação: ____ ____ ____ ____");

        getCreditCard.get().setCardHolderName(creditCardDTO.getCardHolderName());
        getCreditCard.get().setCardNumber(creditCardDTO.getCardNumber());
        getCreditCard.get().setExpiration(creditCardDTO.getExpiration());
        getCreditCard.get().setParticipant(getCreditCard.get().getParticipant());

        CreditCard savedCreditCard = creditCardRepository.save(getCreditCard.get());

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
