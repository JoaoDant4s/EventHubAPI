package com.imd.web2.user.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.imd.web2.user.resources.dto.SaveParticipantDTO;
import com.imd.web2.user.resources.dto.UpdateParticipantDTO;
import com.imd.web2.user.resources.dto.UpdateParticipantInfoDTO;
import com.imd.web2.user.resources.dto.UserDTO;
import com.imd.web2.user.resources.exceptions.ContactNotValidException;
import com.imd.web2.user.resources.exceptions.CpfNotValidException;
import com.imd.web2.user.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.user.resources.exceptions.EmailNotValidException;
import com.imd.web2.user.resources.exceptions.NotFoundException;
import com.imd.web2.user.resources.exceptions.NullParameterException;
import com.imd.web2.user.resources.exceptions.PasswordNotValidException;
import com.imd.web2.user.resources.exceptions.RestSuccessMessage;
import com.imd.web2.user.services.IParticipantService;

import java.util.List;

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

    @PutMapping("/updateInfo")
    public UserDTO participantInfoUpdate(@RequestBody UpdateParticipantInfoDTO participant){
        try {
            UserDTO userDTO = participantService.updateInfo(participant);
            return userDTO;
        } catch (NullParameterException | EmailNotValidException | CpfNotValidException | ContactNotValidException | DateOutOfRangeException e) {
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
