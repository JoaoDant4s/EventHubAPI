package imd.eventuhub.service.Person.Interfaces;

import imd.eventuhub.model.Attraction;
import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IAttractionService {

    public void save(Attraction attraction);
    public void delete(Attraction attraction);
    public Optional<Attraction> getById(Integer id);
    public Optional<Person> getByCpf(String cpf);
    public List<Attraction> getList();

}
