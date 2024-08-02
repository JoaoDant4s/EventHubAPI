package com.imd.web2.user.services;

import org.springframework.stereotype.Service;

import com.imd.web2.user.resources.dto.AttractionDTO;
import com.imd.web2.user.resources.dto.SaveAttractionDTO;
import com.imd.web2.user.resources.dto.SaveAttractionUserDTO;
import com.imd.web2.user.resources.dto.UpdateAttractionDTO;
import com.imd.web2.user.resources.dto.UpdateAttractionInfoDTO;
import com.imd.web2.user.resources.dto.UserDTO;
import com.imd.web2.user.resources.exceptions.ContactNotValidException;
import com.imd.web2.user.resources.exceptions.CpfNotValidException;
import com.imd.web2.user.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.user.resources.exceptions.EmailNotValidException;
import com.imd.web2.user.resources.exceptions.NotFoundException;
import com.imd.web2.user.resources.exceptions.NullParameterException;
import com.imd.web2.user.resources.exceptions.PasswordNotValidException;

import java.util.List;

@Service
public interface IAttractionService {

    public List<UserDTO> getList();
    public AttractionDTO getById(Integer attractionId) throws NotFoundException;
    public boolean isValid(SaveAttractionDTO attractionDTO) throws NullParameterException, ContactNotValidException;
    public UserDTO save(SaveAttractionUserDTO object) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, ContactNotValidException, DateOutOfRangeException;
    public UserDTO update(UpdateAttractionDTO attractionUserDTO) throws  NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, ContactNotValidException, DateOutOfRangeException;
    public UserDTO updateInfo(UpdateAttractionInfoDTO attrDTO) throws NullParameterException, CpfNotValidException, ContactNotValidException, DateOutOfRangeException;
    public void delete(Integer id);
}
