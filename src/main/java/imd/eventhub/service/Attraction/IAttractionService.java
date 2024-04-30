package imd.eventhub.service.Attraction;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.SaveAttractionUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IAttractionService {

    public List<Attraction> getList();
    public Optional<Attraction> getById(Integer id);
    public Attraction save(SaveAttractionDTO attractionDTO);
    public User save(SaveAttractionUserDTO object);
    public Optional<Attraction> update(Attraction attraction, Integer id);
    public void delete(Integer id);
}
