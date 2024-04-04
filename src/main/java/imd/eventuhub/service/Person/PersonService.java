package imd.eventuhub.service.Person;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import imd.eventuhub.repository.IAttractionRepository;
import imd.eventuhub.repository.IParticipantRepository;
import imd.eventuhub.repository.IPersonRepository;
import imd.eventuhub.service.Person.Interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonService implements IPersonService {

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    IParticipantRepository participantRepository;

    @Autowired
    IAttractionRepository attractionRepository;

    @Override
    public void save(Person person){
        personRepository.save(person);
    }

    @Override
    public void delete(Person person){
        personRepository.delete(person);
    }

    @Override
    public Person getById(Integer id){
        return personRepository.findById(id).map(person -> {
            return person;
        }).orElseThrow(() -> null);
    }

    @Override
    public Optional<Person> getByCpf(String cpf){
        Optional<Person> participant = participantRepository.findByCpf(cpf);
        Optional<Person> attraction = attractionRepository.findByCpf(cpf);

        if(participant.isPresent()){
            return participant;
        } else return attraction;
    }

    @Override
    public List<Person> getList(){
        return personRepository.findAll();
    }

}