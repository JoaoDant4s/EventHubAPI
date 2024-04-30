package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.SaveUserDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Object> getUserById(@PathVariable Integer id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id).get());
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody SaveUserDTO user){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        } catch(NotFoundException | CpfNotValidException | DateOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UserUpdate(@PathVariable Integer id, @RequestBody SaveUserDTO user){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(user, id));
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(CpfNotValidException | DateOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> UserDelete(@PathVariable Integer id){
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Usuário apagado com sucesso!"));
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

}
