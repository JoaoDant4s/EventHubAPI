package com.imd.web2.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imd.web2.user.resources.dto.SaveUserDTO;
import com.imd.web2.user.resources.dto.UpdateUserDTO;
import com.imd.web2.user.resources.dto.UserDTO;
import com.imd.web2.user.resources.exceptions.CpfNotValidException;
import com.imd.web2.user.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.user.resources.exceptions.EmailNotValidException;
import com.imd.web2.user.resources.exceptions.NotFoundException;
import com.imd.web2.user.resources.exceptions.NullParameterException;
import com.imd.web2.user.resources.exceptions.PasswordNotValidException;

@Service
public interface IUserService {
     public List<UserDTO> getList();
    public Optional<UserDTO> getById(Integer id);
    public boolean isValid(SaveUserDTO userDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;
    public boolean updateIsValid(SaveUserDTO userDTO, Integer userId) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;
    public boolean updateInfoIsValid(UpdateUserDTO userDTO, Integer userId) throws NullParameterException, CpfNotValidException, DateOutOfRangeException;
    public void delete(Integer id);
    public Optional<UserDTO> getUserByCPF(String cpf);
    public Optional<UserDTO> getUserByEmail(String email) throws NotFoundException;
    public void setUserAttraction(Integer userId, Integer attractionId);
    public void setUserParticipant(Integer userId, Integer participantId);
}
