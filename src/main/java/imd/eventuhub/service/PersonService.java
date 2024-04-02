package imd.eventuhub.service;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import imd.eventuhub.repository.IParticipantRepository;
import imd.eventuhub.repository.IPersonRepository;
import jakarta.servlet.http.Part;
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

    @Override
    public void save(Person person){
        personRepository.save(person);
    }

    @Override
    public void delete(Person aluno){
        personRepository.delete(aluno);
    }

    @Override
    public Person getPersonById(Integer id){
        return personRepository.findById(id).map(person -> {
            return person;
        }).orElseThrow(() -> null);
    }

    @Override
    public List<Person> getPersonList(){
        return personRepository.findAll();
    }

    @Override
    public List<Participant> getParticipantList(){
        return participantRepository.findAll();
    }

}
