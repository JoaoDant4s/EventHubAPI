package imd.eventhub.service.User;

import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.SaveUserDTO;
import imd.eventhub.service.IBaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {

    public List<User> getList();
    public Optional<User> getById(Integer id);
    public User save(SaveUserDTO userDTO);
    public Optional<User> update(SaveUserDTO userDTO, Integer id);
    public void delete(Integer id);
    public Optional<User> getUserByCPF(String cpf);
    public void setUserAttraction(Integer userId, Integer attractionId);
}
