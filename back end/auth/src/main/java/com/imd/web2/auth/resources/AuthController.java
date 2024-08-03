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
import com.imd.web2.auth.resources.dto.TokenDTO;
import com.imd.web2.auth.resources.exceptions.PasswordNotValidException;
import com.imd.web2.auth.security.JwtService;
import com.imd.web2.auth.services.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

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
}
