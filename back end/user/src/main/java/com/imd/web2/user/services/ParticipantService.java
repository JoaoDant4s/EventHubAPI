package com.imd.web2.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.imd.web2.user.model.Participant;
import com.imd.web2.user.model.User;
import com.imd.web2.user.repository.IParticipantRepository;
import com.imd.web2.user.repository.IUserRepository;
import com.imd.web2.user.resources.dto.SaveParticipantDTO;
import com.imd.web2.user.resources.dto.UserDTO;
import com.imd.web2.user.resources.dto.SaveUserDTO;
import com.imd.web2.user.resources.dto.UpdateUserDTO;
import com.imd.web2.user.resources.dto.UpdateParticipantDTO;
import com.imd.web2.user.resources.dto.UpdateParticipantInfoDTO;
import com.imd.web2.user.resources.exceptions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ParticipantService implements IParticipantService{

    IParticipantRepository participantRepository;

    IUserRepository userRepository;

    @Autowired
    IUserService userService;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getList() {
        return userRepository.getParticipants().stream().map(user-> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if(user.get().getParticipant()!=null){
            throw new NotFoundException("participante não encontrado");
        }

        UserDTO userDTO = UserDTO.toUserDTO(user.get());

        return userDTO;
    }

    public Optional<Participant> getParticipantById(Integer id){
        Optional<Participant> participant = participantRepository.findById(id);

        if(participant.isEmpty()){
            throw new NotFoundException("participante não encontrado");
        }
        return participant;
    }

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

    @Override
    public UserDTO update(UpdateParticipantDTO partDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {

        Optional<User> user = userRepository.findById(partDTO.getId());
        boolean checkUser = userService.updateIsValid(
                new SaveUserDTO(
                        partDTO.getName(),
                        partDTO.getCpf(),
                        partDTO.getBirthDate().toString(),
                        partDTO.getEmail(),
                        partDTO.getPassword(),
                        partDTO.getConfirmPassword()
                ), partDTO.getId()
        );


        if(checkUser) {

            user.get().setName(partDTO.getName());
            user.get().setCpf(partDTO.getCpf());
            user.get().setEmail(partDTO.getEmail());
            user.get().setBirthDate(partDTO.getBirthDate());
            user.get().setAge((int) ChronoUnit.YEARS.between(user.get().getBirthDate(), LocalDate.now()));
            user.get().setPassword(passwordEncoder.encode(partDTO.getPassword()));
            User savedUser = userRepository.save(user.get());

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }
        return null;
    }

    @Override
    public UserDTO updateInfo(UpdateParticipantInfoDTO partDTO) throws NullParameterException, CpfNotValidException, DateOutOfRangeException {

        Optional<User> user = userRepository.findById(partDTO.getId());
        boolean checkUser = userService.updateInfoIsValid(
                new UpdateUserDTO(
                        partDTO.getName(),
                        partDTO.getCpf(),
                        partDTO.getBirthDate().toString()
                ), partDTO.getId()
        );


        if(checkUser) {
            user.get().setName(partDTO.getName());
            user.get().setCpf(partDTO.getCpf());
            user.get().setBirthDate(partDTO.getBirthDate());
            user.get().setAge((int) ChronoUnit.YEARS.between(user.get().getBirthDate(), LocalDate.now()));
            User savedUser = userRepository.save(user.get());

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Optional<Participant> participant = participantRepository.findById(id);
        if(participant.isEmpty()){
            throw new NotFoundException("Participante não encontrado");
        }

        Optional<User> user = userRepository.findByParticipant_id(participant.get().getId());
        if(user.isPresent()){
            user.get().setParticipant(null);
            userRepository.save(user.get());
        }

        participantRepository.delete(participant.get());
    }


}
