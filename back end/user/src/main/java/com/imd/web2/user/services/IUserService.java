package com.imd.web2.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    public List<UserDTO> getList();

    public Optional<UserDTO> getById(Integer id);

    public boolean isValid(SaveUserDTO userDTO) throws NullParameterException, EmailNotValidException,
            PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;

    public boolean updateIsValid(SaveUserDTO userDTO, Integer userId) throws NullParameterException,
            EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;

    public void delete(Integer id);

    public Optional<UserDTO> getUserByCPF(String cpf);

    public Optional<UserDTO> getUserByEmail(String email) throws NotFoundException;

    public void setUserAttraction(Integer userId, Integer attractionId);

    public void setUserParticipant(Integer userId, Integer participantId);

    public UserDetails authentication(User user) throws PasswordNotValidException;
}
