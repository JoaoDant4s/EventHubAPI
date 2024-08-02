package com.imd.web2.auth.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.imd.web2.auth.feignclients.UserFeignClient;
import com.imd.web2.auth.model.User;
import com.imd.web2.auth.resources.exceptions.*;

import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public class AuthService implements UserDetailsService{
    @Autowired
    private UserFeignClient userClient;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = Optional.ofNullable(userClient.findByEmail(email)
        .getBody());

        if(user.isEmpty()){
            throw new NotFoundException("Email ou senha incorreta");
        }

        List<String> roles = new ArrayList<>();
        if(user.get().isAdmin()){
            roles.add("ADMIN");
            roles.add("USER");
        } else if(user.get().getAttraction() != null) {
            roles.add("ATTRACTION");
            roles.add("USER");
        } else if(user.get().isPromoter()){
            roles.add("PROMOTER");
        } else {
            roles.add("USER");
        }

        // Cria e retorna o objeto UserDetails com os detalhes do usuário
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .roles(roles.toArray(new String[roles.size()-1]))
                .build();

    }

    public UserDetails authentication(User user) throws PasswordNotValidException {
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        boolean checkPassword = passwordEncoder.matches(user.getPassword(), userDetails.getPassword());
        if(checkPassword){
            return userDetails;
        }

        throw new PasswordNotValidException("Email ou senha incorretos");
    }
}
