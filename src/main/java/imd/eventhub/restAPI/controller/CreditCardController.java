package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.*;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.creditCard.SaveCreditCardDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.service.CreditCard.ICreditCardService;
import imd.eventhub.service.Participant.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/creditCard")
public class CreditCardController {

    @Autowired
    ICreditCardService creditCardService;


    @GetMapping("/{id}")
    public ResponseEntity<Object> getCreditCardById(@PathVariable Integer id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(creditCardService.getById(id).get());
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveCreditCard(@RequestBody SaveCreditCardDTO creditCardDTODTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(creditCardService.save(creditCardDTODTO));
        } catch(NotFoundException | CreditCardNotValidException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> creditCardDelete(@PathVariable Integer id){
        try {
            creditCardService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Cartão de crédito apagado com sucesso!"));
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

}
