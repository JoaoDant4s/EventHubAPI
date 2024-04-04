package imd.eventuhub.service.Person;

import imd.eventuhub.model.Attraction;
import imd.eventuhub.model.Person;
import imd.eventuhub.repository.IAttractionRepository;
import imd.eventuhub.service.Person.Interfaces.IAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AttractionService implements IAttractionService {

    @Autowired
    IAttractionRepository attractionRepository;

    @Override
    public void save(Attraction attraction){
        attractionRepository.save(attraction);
    }

    @Override
    public void delete(Attraction participant){
        attractionRepository.delete(participant);
    }

    @Override
    public Optional<Attraction> getById(Integer id){
        return attractionRepository.findById(id);
    }

    @Override
    public Optional<Person> getByCpf(String cpf){
        return attractionRepository.findByCpf(cpf);
    }

    @Override
    public List<Attraction> getList(){
        return attractionRepository.findAll();
    }

}
