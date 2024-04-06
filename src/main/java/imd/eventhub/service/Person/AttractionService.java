package imd.eventhub.service.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.Person;
import imd.eventhub.repository.IAttractionRepository;
import imd.eventhub.service.Person.Interfaces.IAttractionService;

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
