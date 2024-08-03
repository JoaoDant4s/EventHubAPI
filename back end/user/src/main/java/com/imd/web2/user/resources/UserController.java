package com.imd.web2.user.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.imd.web2.user.resources.dto.UserDTO;
import com.imd.web2.user.resources.exceptions.NotFoundException;
import com.imd.web2.user.resources.exceptions.RestErrorMessage;
import com.imd.web2.user.resources.exceptions.RestSuccessMessage;
import com.imd.web2.user.services.IUserService;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping
    public ResponseEntity<Object> getUserList(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getList());
    }
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id){
        try {
            return userService.getById(id).get();
        } catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping("/getUserByEmail/{email}")
    public UserDTO getUserByEmail(@PathVariable String email){
        try {
            return userService.getUserByEmail(email).get();
        } catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> userDelete(@PathVariable Integer id){
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Usuário apagado com sucesso!"));
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }
}
