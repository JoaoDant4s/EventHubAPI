package imd.eventhub.service.Person.Interfaces;

import org.springframework.stereotype.Service;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;

import java.util.List;
import java.util.Optional;

@Service
public interface IAttractionService {

    public void save(Attraction attraction);
    public void delete(Attraction attraction);
    public Optional<Attraction> getById(Integer id);
    public Optional<Person> getByCpf(String cpf);
    public List<Attraction> getList();

}
