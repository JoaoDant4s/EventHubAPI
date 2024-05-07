package imd.eventhub.service.User;

import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {

    public List<UserDTO> getList();
    public Optional<UserDTO> getById(Integer id);
    public UserDTO save(SaveUserDTO userDTO);
    public UserDTO update(UpdateUserDTO userDTO);
    public void delete(Integer id);
    public Optional<UserDTO> getUserByCPF(String cpf);
    public void setUserAttraction(Integer userId, Integer attractionId);
    public void setUserParticipant(Integer userId, Integer participantId);
    public Optional<UserDTO> getUserByAttractionId(Integer attractionId);
}
