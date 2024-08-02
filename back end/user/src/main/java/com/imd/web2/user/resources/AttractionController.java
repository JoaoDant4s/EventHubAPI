package com.imd.web2.user.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.imd.web2.user.resources.dto.AttractionDTO;
import com.imd.web2.user.resources.dto.SaveAttractionUserDTO;
import com.imd.web2.user.resources.dto.UpdateAttractionDTO;
import com.imd.web2.user.resources.dto.UpdateAttractionInfoDTO;
import com.imd.web2.user.resources.dto.UserDTO;
import com.imd.web2.user.resources.exceptions.ContactNotValidException;
import com.imd.web2.user.resources.exceptions.CpfNotValidException;
import com.imd.web2.user.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.user.resources.exceptions.EmailNotValidException;
import com.imd.web2.user.resources.exceptions.NotFoundException;
import com.imd.web2.user.resources.exceptions.NullParameterException;
import com.imd.web2.user.resources.exceptions.PasswordNotValidException;
import com.imd.web2.user.resources.exceptions.RestSuccessMessage;
import com.imd.web2.user.services.IAttractionService;

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
