package com.imd.web2.auth.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.imd.web2.auth.resources.dto.TokenDTO;

public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

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
