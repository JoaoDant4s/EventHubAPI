package com.imd.web2.user.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imd.web2.user.model.Attraction;
import com.imd.web2.user.model.Participant;
import com.imd.web2.user.model.User;
import com.imd.web2.user.repository.IAttractionRepository;
import com.imd.web2.user.repository.IParticipantRepository;
import com.imd.web2.user.repository.IUserRepository;
import com.imd.web2.user.resources.dto.SaveUserDTO;
import com.imd.web2.user.resources.dto.UpdateUserDTO;
import com.imd.web2.user.resources.dto.UserDTO;
import com.imd.web2.user.resources.exceptions.CpfNotValidException;
import com.imd.web2.user.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.user.resources.exceptions.EmailNotValidException;
import com.imd.web2.user.resources.exceptions.NotFoundException;
import com.imd.web2.user.resources.exceptions.NullParameterException;
import com.imd.web2.user.resources.exceptions.PasswordNotValidException;

@Component
public class UserService implements IUserService {

    IUserRepository userRepository;

    IAttractionRepository attractionRepository;
    @Autowired
    IAttractionService attractionService;

    IParticipantRepository participantRepository;


    @Override
    public List<UserDTO> getList() {
        return userRepository.findAll().stream().map(user -> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getById(Integer id) {
        Optional<UserDTO> showUser = userRepository.findById(id).stream().findAny().map(user -> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        });
        if (showUser.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        return showUser;
    }

    @Override
    public void delete(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        userRepository.delete(user.get());
    }

    @Override
    public Optional<UserDTO> getUserByCPF(String cpf) {
        return userRepository.findByCpf(cpf).stream().findAny().map(user -> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        });
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).stream().findAny().map(user -> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        });
    }

    @Override
    public void setUserAttraction(Integer userId, Integer attractionId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Attraction> attraction = attractionRepository.findById(attractionId);

        if (user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        if (attraction.isEmpty()) {
            throw new NotFoundException("Atração não encontrada");
        }

        user.get().setAttraction(attraction.get());
        userRepository.save(user.get());
    }

    @Override
    public void setUserParticipant(Integer userId, Integer participantId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Participant> participant = participantRepository.findById(participantId);

        if (user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        if (participant.isEmpty()) {
            throw new NotFoundException("Atração não encontrada");
        }

        user.get().setParticipant(participant.get());
        userRepository.save(user.get());
    }

    @Override
    public boolean updateInfoIsValid(UpdateUserDTO userDTO, Integer userId) throws NullParameterException, CpfNotValidException, DateOutOfRangeException {
        Optional<UserDTO> userWithSameCPF = getUserByCPF(userDTO.getCpf());
        Optional<User> user = userRepository.findById(userId);

        if(userDTO.getName() == null) throw new NullParameterException("O nome não foi encontrado");
        if(userDTO.getCpf() == null) throw new NullParameterException("O CPF não foi encontrado");
        if(userDTO.getBirthDate() == null) throw new NullParameterException("A data de nascimento não foi encontrada");
        if(!checkCpfIsValid(userDTO.getCpf())) throw new CpfNotValidException("O CPF/CNPJ digitado não segue o padrão");
        if(userWithSameCPF.isPresent() && !user.get().getCpf().equals(userDTO.getCpf())) throw new CpfNotValidException("O CPF digitado já está associado a um outro usuário");
        if(userDTO.getBirthDate().isAfter(LocalDate.now())) throw new DateOutOfRangeException("A data informada é maior do que a data de hoje");

        return true;
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

    @Override
    public boolean updateIsValid(SaveUserDTO userDTO, Integer userId) throws NullParameterException,
            EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {
        Optional<UserDTO> userWithSameCPF = getUserByCPF(userDTO.getCpf());
        Optional<UserDTO> userWithSameEmail = getUserByEmail(userDTO.getEmail());
        Optional<User> user = userRepository.findById(userId);

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
        if (userWithSameEmail.isPresent() && !user.get().getEmail().equals(userDTO.getEmail()))
            throw new EmailNotValidException("O Email digitado já está associado a um outro usuário");
        if (userDTO.getPassword().length() < 8)
            throw new PasswordNotValidException("A senha precisa ter no mínimo 8 caracteres");
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword()))
            throw new PasswordNotValidException("As senhas não são iguais");
        if (!checkCpfIsValid(userDTO.getCpf()))
            throw new CpfNotValidException(
                    String.format("O CPF digitado ('%s') não segue o padrão 000.000.000-00", userDTO.getCpf()));
        if (userWithSameCPF.isPresent() && !user.get().getCpf().equals(userDTO.getCpf()))
            throw new CpfNotValidException("O CPF digitado já está associado a um outro usuário");
        if (userDTO.getBirthDate().isAfter(LocalDate.now()))
            throw new DateOutOfRangeException("A data informada é maior do que a data de hoje");

        return true;
    }

    public static boolean checkCpfIsValid(String cpf) {

        Pattern regex = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
        return regex.matcher(cpf).find();

    }
}
