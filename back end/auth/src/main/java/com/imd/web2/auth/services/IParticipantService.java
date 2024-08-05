package com.imd.web2.auth.services;

import org.springframework.stereotype.Service;

import com.imd.web2.auth.resources.dto.SaveParticipantDTO;
import com.imd.web2.auth.resources.dto.UserDTO;
import com.imd.web2.auth.resources.exceptions.CpfNotValidException;
import com.imd.web2.auth.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.auth.resources.exceptions.EmailNotValidException;
import com.imd.web2.auth.resources.exceptions.NullParameterException;
import com.imd.web2.auth.resources.exceptions.PasswordNotValidException;

@Service
public interface IParticipantService {
      public UserDTO save(SaveParticipantDTO participantDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;
}
