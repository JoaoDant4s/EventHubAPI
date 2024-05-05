package imd.eventhub.service.Attraction;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IAttractionService {

    public List<User> getList();
    public Optional<Attraction> getById(Integer id);
    public Attraction save(SaveAttractionDTO attractionDTO);
    public User save(SaveAttractionUserDTO object);

    public Attraction update(UpdateAttractionDTO attractionUserDTO);

    public void delete(Integer id);
}
