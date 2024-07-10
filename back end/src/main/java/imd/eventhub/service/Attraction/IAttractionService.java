package imd.eventhub.service.Attraction;

import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.ShowAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IAttractionService {

    public List<UserDTO> getList();
    public Optional<UserDTO> getById(Integer attractionId);
    public Attraction save(SaveAttractionDTO attractionDTO);
    public UserDTO save(SaveAttractionUserDTO object) throws NotFoundException, CpfNotValidException, DateOutOfRangeException;

    public ShowAttractionUserDTO update(UpdateAttractionDTO attractionUserDTO);

    public void delete(Integer id);
}
