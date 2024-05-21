package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.ContactNotValidException;
import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import imd.eventhub.restAPI.dto.participant.SaveParticipantDTO;
import imd.eventhub.restAPI.dto.participant.UpdateParticipantDTO;
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
            return ResponseEntity.status(HttpStatus.OK).body(participantService.getById(id));
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveUserParticipant(@RequestBody SaveParticipantDTO participantDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(participantService.save(participantDTO));
        } catch(NotFoundException | CpfNotValidException | DateOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(500, HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }
    @PutMapping
    public ResponseEntity<Object> participantUpdate(@RequestBody UpdateParticipantDTO participant){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(participantService.update(participant));
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(CpfNotValidException | DateOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(400, HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> participantDelete(@PathVariable Integer id){
        try {
            participantService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Participante apagado com sucesso!"));
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

}
