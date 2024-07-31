package imd.eventhub.service.Attraction;

import imd.eventhub.exception.*;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.attraction.*;
import imd.eventhub.restAPI.dto.participant.UpdateParticipantInfoDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
