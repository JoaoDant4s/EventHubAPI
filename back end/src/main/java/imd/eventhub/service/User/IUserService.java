package imd.eventhub.service.User;

import imd.eventhub.exception.*;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.participant.SaveParticipantDTO;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {

    public List<UserDTO> getList();
    public List<UserDTO> getPromoterList();
    public UserDTO savePromoter(SaveParticipantDTO partDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;
    public Optional<UserDTO> getById(Integer id);
    public boolean isValid(SaveUserDTO userDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;
    public boolean updateIsValid(SaveUserDTO userDTO, Integer userId) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException;
    public boolean updateInfoIsValid(UpdateUserDTO userDTO, Integer userId) throws NullParameterException, CpfNotValidException, DateOutOfRangeException;
    public void delete(Integer id);
    public Optional<UserDTO> getUserByCPF(String cpf);
    public Optional<UserDTO> getUserByEmail(String email) throws NotFoundException;
    public void setUserAttraction(Integer userId, Integer attractionId);
    public void setUserParticipant(Integer userId, Integer participantId);
    public UserDetails authentication(User user) throws PasswordNotValidException;
}
