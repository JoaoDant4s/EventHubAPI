package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.SaveAttractionUserDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.service.Attraction.IAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/attraction")
public class AttractionController {

    @Autowired
    IAttractionService attractionService;

    @PostMapping
    public ResponseEntity<Object> saveAttraction(@RequestBody SaveAttractionDTO attractionDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(attractionService.save(attractionDTO));
        } catch(NotFoundException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @PostMapping("/saveUser")
    public ResponseEntity<Object> saveUserAttraction(@RequestBody SaveAttractionUserDTO attractionDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(attractionService.save(attractionDTO));
        } catch(NotFoundException | CpfNotValidException | DateOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }
}
