package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.*;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.user.CredenciaisDTO;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
import imd.eventhub.restAPI.dto.user.TokenDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.security.JwtService;
import imd.eventhub.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @GetMapping
    public ResponseEntity<Object> getUserList(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id).get());
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody SaveUserDTO user){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        } catch(NotFoundException | CpfNotValidException | DateOutOfRangeException | EmailNotValidException | PasswordNotValidException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(400, HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Object> userUpdate(@RequestBody UpdateUserDTO user){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(user));
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(CpfNotValidException | DateOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(400, HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
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

    @PostMapping("/auth")
    public ResponseEntity<Object>  authentication(@RequestBody CredenciaisDTO credenciais){
        try{
            User user = new User();
            user.setEmail(credenciais.getEmail());
            user.setPassword(credenciais.getPassword());

            UserDetails authenticatedUser = userService.authentication(user);
            String token = jwtService.gerarToken(user);
            return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(user.getEmail(), token));
        } catch (PasswordNotValidException exception ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestErrorMessage(401, HttpStatus.UNAUTHORIZED, exception.getMessage()));
        }
    }

}
