package com.imd.web2.auth.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.web2.auth.repository.IParticipantRepository;
import com.imd.web2.auth.repository.IUserRepository;
import com.imd.web2.auth.resources.dto.*;
import com.imd.web2.auth.resources.exceptions.*;

@Component
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IParticipantRepository participantRepository;

    public Optional<UserDTO> getUserByCPF(String cpf) {
        return userRepository.findByCpf(cpf).stream().findAny().map(user -> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        });
    }

    public Optional<UserDTO> getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).stream().findAny().map(user -> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        });
    }
    
    public static boolean checkCpfIsValid(String cpf) {

        Pattern regex = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
        return regex.matcher(cpf).find();

    }

    @Override
    public boolean isValid(SaveUserDTO userDTO) throws NullParameterException, EmailNotValidException,
            PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {
        Optional<UserDTO> userWithSameCPF = getUserByCPF(userDTO.getCpf());
        Optional<UserDTO> userWithSameEmail = getUserByEmail(userDTO.getEmail());

        if (userDTO.getName() == null)
            throw new NullParameterException("O nome não foi encontrado");
        if (userDTO.getEmail() == null)
            throw new NullParameterException("O email não foi encontrado");
        if (userDTO.getCpf() == null)
            throw new NullParameterException("O CPF não foi encontrado");
        if (userDTO.getBirthDate() == null)
            throw new NullParameterException("A data de nascimento não foi encontrada");
        if (userDTO.getPassword() == null)
            throw new NullParameterException("A senha não foi encontrada");
        if (userWithSameEmail.isPresent())
            throw new EmailNotValidException("O Email digitado já está associado a um outro usuário");
        if (userDTO.getPassword().length() < 8)
            throw new PasswordNotValidException("A senha precisa ter no mínimo 8 caracteres");
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword()))
            throw new PasswordNotValidException("As senhas não são iguais");
        if (!checkCpfIsValid(userDTO.getCpf()))
            throw new CpfNotValidException(
                    String.format("O CPF digitado ('%s') não segue o padrão 000.000.000-00", userDTO.getCpf()));
        if (userWithSameCPF.isPresent())
            throw new CpfNotValidException("O CPF digitado já está associado a um outro usuário");
        if (userDTO.getBirthDate().isAfter(LocalDate.now()))
            throw new DateOutOfRangeException("A data informada é maior do que a data de hoje");

        return true;
    }
}
