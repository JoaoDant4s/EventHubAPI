package com.imd.web2.user.services;
import org.springframework.stereotype.Service;

import com.imd.web2.user.model.Participant;
import com.imd.web2.user.resources.dto.SaveParticipantDTO;
import com.imd.web2.user.resources.dto.UpdateParticipantDTO;
import com.imd.web2.user.resources.dto.UpdateParticipantInfoDTO;
import com.imd.web2.user.resources.dto.UserDTO;
import com.imd.web2.user.resources.exceptions.CpfNotValidException;
import com.imd.web2.user.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.user.resources.exceptions.EmailNotValidException;
import com.imd.web2.user.resources.exceptions.NullParameterException;
import com.imd.web2.user.resources.exceptions.PasswordNotValidException;

import java.util.List;
import java.util.Optional;

@Service
public interface IParticipantService {

    public List<UserDTO> getList();
    public UserDTO getById(Integer id);
    public Optional<Participant> getParticipantById(Integer id);
    public UserDTO save(SaveParticipantDTO participantDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;

    public UserDTO update(UpdateParticipantDTO attractionUserDTO) throws EmailNotValidException, PasswordNotValidException, CpfNotValidException, NullParameterException;

    public UserDTO updateInfo(UpdateParticipantInfoDTO attractionUserDTO) throws NullParameterException, EmailNotValidException, CpfNotValidException, DateOutOfRangeException;
    public void delete(Integer id);
}
