package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.*;
import imd.eventhub.model.Event;
import imd.eventhub.restAPI.dto.participant.SaveParticipantDTO;
import imd.eventhub.restAPI.dto.participant.UpdateParticipantDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.service.Participant.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/participant")
public class ParticipantController {

    @Autowired
    IParticipantService participantService;

    @GetMapping
    public List<UserDTO> getParticipantList(){
        return participantService.getList();
    }

    @GetMapping("/{id}")
    public UserDTO getParticipantById(@PathVariable Integer id){
        try {
            return participantService.getById(id);
        } catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public UserDTO saveUserParticipant(@RequestBody SaveParticipantDTO participantDTO){
        try {
            UserDTO userDTO = participantService.save(participantDTO);
            return userDTO;
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NullParameterException | EmailNotValidException | PasswordNotValidException | CpfNotValidException | DateOutOfRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @PutMapping
    public UserDTO participantUpdate(@RequestBody UpdateParticipantDTO participant){
        try {
            UserDTO userDTO = participantService.update(participant);
            return userDTO;
        } catch (NullParameterException | EmailNotValidException | PasswordNotValidException | CpfNotValidException | ContactNotValidException | DateOutOfRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> participantDelete(@PathVariable Integer id){
        try {
            participantService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Participante apagado com sucesso!"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
