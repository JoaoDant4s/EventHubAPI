package imd.eventhub.service.Participant;

import imd.eventhub.exception.*;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.Participant;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import imd.eventhub.restAPI.dto.participant.ParticipantDTO;
import imd.eventhub.restAPI.dto.participant.SaveParticipantDTO;
import imd.eventhub.restAPI.dto.participant.UpdateParticipantDTO;
import imd.eventhub.restAPI.dto.participant.UpdateParticipantInfoDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import imd.eventhub.service.IBaseService;
import org.springframework.stereotype.Service;

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
