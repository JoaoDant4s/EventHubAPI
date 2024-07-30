package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.*;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.attraction.AttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionInfoDTO;
import imd.eventhub.restAPI.dto.participant.UpdateParticipantInfoDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.service.Attraction.IAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/attraction")
public class AttractionController {

    @Autowired
    IAttractionService attractionService;

    @GetMapping
    public List<UserDTO> getAttractionList(){
        return attractionService.getList();
    }

    @GetMapping("/{id}")
    public AttractionDTO getUserByAttractionId(@PathVariable Integer id){
        try {
            return attractionService.getById(id);
        } catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public UserDTO saveUserAttraction(@RequestBody SaveAttractionUserDTO attractionDTO){
        try {
            UserDTO userDTO = attractionService.save(attractionDTO);
            return userDTO;
        } catch (NullParameterException | EmailNotValidException | PasswordNotValidException | CpfNotValidException | ContactNotValidException | DateOutOfRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PutMapping
    public UserDTO attractionUpdate(@RequestBody UpdateAttractionDTO attraction){
        try {
            UserDTO userDTO = attractionService.update(attraction);
            return userDTO;
        } catch (NullParameterException | EmailNotValidException | PasswordNotValidException | CpfNotValidException | ContactNotValidException | DateOutOfRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/updateInfo")
    public UserDTO attractionInfoUpdate(@RequestBody UpdateAttractionInfoDTO attraction){
        try {
            UserDTO userDTO = attractionService.updateInfo(attraction);
            return userDTO;
        } catch (NullParameterException | CpfNotValidException | ContactNotValidException | DateOutOfRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> attractionDelete(@PathVariable Integer id){
        try {
            attractionService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Atração apagada com sucesso!"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
