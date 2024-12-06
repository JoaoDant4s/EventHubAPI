package com.imd.web2.auth.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imd.web2.auth.model.User;
import com.imd.web2.auth.resources.dto.CredenciaisDTO;
import com.imd.web2.auth.resources.dto.SaveParticipantDTO;
import com.imd.web2.auth.resources.dto.TokenDTO;
import com.imd.web2.auth.resources.dto.UserDTO;
import com.imd.web2.auth.resources.exceptions.CpfNotValidException;
import com.imd.web2.auth.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.auth.resources.exceptions.EmailNotValidException;
import com.imd.web2.auth.resources.exceptions.NotFoundException;
import com.imd.web2.auth.resources.exceptions.NullParameterException;
import com.imd.web2.auth.resources.exceptions.PasswordNotValidException;
import com.imd.web2.auth.security.JwtService;
import com.imd.web2.auth.services.AuthService;
import com.imd.web2.auth.services.IParticipantService;
import com.imd.web2.auth.services.ParticipantService;
import com.netflix.discovery.converters.Auto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    JwtService jwtService;

    @PostMapping
    public TokenDTO authentication(@RequestBody CredenciaisDTO credenciais){
        try{
            User user = new User();
            user.setEmail(credenciais.getEmail());
            user.setPassword(credenciais.getPassword());

            UserDetails authenticatedUser = authService.authentication(user);
            String token = jwtService.gerarToken(user);

            List<String> roles = authenticatedUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new TokenDTO(user.getEmail(), token, roles);
        } catch (PasswordNotValidException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        Boolean result = jwtService.tokenValido(token);
        return result ? "Valid token" : "Not valid token";
    }
    
    @PostMapping("/create")
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
}
