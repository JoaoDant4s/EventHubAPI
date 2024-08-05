package com.imd.web2.user.resources;

import com.imd.web2.user.resources.dto.CreditCardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imd.web2.user.resources.dto.SaveCreditCardDTO;
import com.imd.web2.user.resources.dto.UpdateCreditCardDTO;
import com.imd.web2.user.resources.exceptions.CreditCardNotValidException;
import com.imd.web2.user.resources.exceptions.NotFoundException;
import com.imd.web2.user.resources.exceptions.RestErrorMessage;
import com.imd.web2.user.resources.exceptions.RestSuccessMessage;
import com.imd.web2.user.services.ICreditCardService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/creditCard")
public class CreditCardController {

    @Autowired
    ICreditCardService creditCardService;


    @GetMapping("/{id}")
    public CreditCardDTO getCreditCardById(@PathVariable Integer id){
        try {
            return creditCardService.getById(id).get();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping("/getByParticipantId/{id}")
    public CreditCardDTO getByParticipantId(@PathVariable Integer id){
        try {
            return creditCardService.getByParticipantId(id).get();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public CreditCardDTO saveCreditCard(@RequestBody SaveCreditCardDTO creditCardDTODTO){
        try{
            return creditCardService.save(creditCardDTODTO);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (CreditCardNotValidException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<Object> creditCardUpdate(@RequestBody UpdateCreditCardDTO creditCardDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(creditCardService.update(creditCardDTO));
        } catch(NotFoundException | CreditCardNotValidException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(400, HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> creditCardDelete(@PathVariable Integer id){
        try {
            creditCardService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Cartão de crédito apagado com sucesso!"));
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

}
