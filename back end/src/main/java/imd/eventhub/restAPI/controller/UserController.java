package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.*;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.user.*;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.security.JwtService;
import imd.eventhub.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/auth")
    public TokenDTO authentication(@RequestBody CredenciaisDTO credenciais){
        try{
            User user = new User();
            user.setEmail(credenciais.getEmail());
            user.setPassword(credenciais.getPassword());

            UserDetails authenticatedUser = userService.authentication(user);
            String token = jwtService.gerarToken(user);

            List<String> roles = authenticatedUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new TokenDTO(user.getEmail(), token, roles);
        } catch (PasswordNotValidException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
