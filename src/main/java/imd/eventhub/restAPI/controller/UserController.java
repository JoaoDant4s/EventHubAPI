package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.User;
import imd.eventhub.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping
    public ResponseEntity<Object> getUserList(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getList());
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody User user){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
