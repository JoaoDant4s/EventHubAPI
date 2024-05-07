package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.ContactNotValidException;
import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.service.Attraction.IAttractionService;
import imd.eventhub.service.Participant.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/participant")
public class ParticipantController {

    @Autowired
    IParticipantService participantService;

    @GetMapping
    public ResponseEntity<Object> getParticipantList(){
        return ResponseEntity.status(HttpStatus.OK).body(participantService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParticipantById(@PathVariable Integer id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(participantService.getById(id).get());
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
        }
    }

}
