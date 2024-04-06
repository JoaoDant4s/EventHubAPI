package imd.eventhub.service.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;
import imd.eventhub.repository.IAttractionRepository;
import imd.eventhub.repository.IParticipantRepository;
import imd.eventhub.repository.IPersonRepository;
import imd.eventhub.service.Person.Interfaces.IPersonService;

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