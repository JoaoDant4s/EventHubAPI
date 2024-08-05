package com.imd.web2.auth.services;

import org.springframework.stereotype.Service;

import com.imd.web2.auth.resources.dto.*;
import com.imd.web2.auth.resources.exceptions.*;

@Service
public interface IUserService {
    public boolean isValid(SaveUserDTO userDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;
}
