package com.imd.web2.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.imd.web2.auth.model.Participant;
import com.imd.web2.auth.model.User;
import com.imd.web2.auth.repository.IParticipantRepository;
import com.imd.web2.auth.repository.IUserRepository;
import com.imd.web2.auth.resources.dto.SaveParticipantDTO;
import com.imd.web2.auth.resources.dto.UserDTO;
import com.imd.web2.auth.resources.dto.SaveUserDTO;
import com.imd.web2.auth.resources.exceptions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ParticipantService implements IParticipantService{

    @Autowired
    IParticipantRepository participantRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public UserDTO save(SaveParticipantDTO partDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {


        boolean checkUser = userService.isValid(
                new SaveUserDTO(
                        partDTO.getName(),
                        partDTO.getCpf(),
                        partDTO.getBirthDate().toString(),
                        partDTO.getEmail(),
                        partDTO.getPassword(),
                        partDTO.getConfirmPassword()
                )
        );

        if(checkUser){
            Participant participant = new Participant();
            Participant savedParticipant = participantRepository.save(participant);

            User user = new User();
            user.setName(partDTO.getName());
            user.setCpf(partDTO.getCpf());
            user.setEmail(partDTO.getEmail());
            user.setBirthDate(partDTO.getBirthDate());
            user.setAge((int) ChronoUnit.YEARS.between(user.getBirthDate(),LocalDate.now()));
            user.setPassword(passwordEncoder.encode(partDTO.getPassword()));
            user.setParticipant(savedParticipant);
            User savedUser = userRepository.save(user);

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }

        return null;
    }
}

