package imd.eventhub.service.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;
import imd.eventhub.repository.IParticipantRepository;
import imd.eventhub.service.Person.Interfaces.IParticipantService;

import java.util.List;
import java.util.Optional;

@Component
public class ParticipantService implements IParticipantService {

    @Autowired
    IParticipantRepository participantRepository;

    @Override
    public void save(Participant participant){
        participantRepository.save(participant);
    }

    @Override
    public void delete(Participant participant){
        participantRepository.delete(participant);
    }

    @Override
    public Optional<Participant> getById(Integer id){
        return participantRepository.findById(id);
    }

    @Override
    public Optional<Person> getByCpf(String cpf){
        return participantRepository.findByCpf(cpf);
    }

    @Override
    public List<Participant> getList(){
        return participantRepository.findAll();
    }

}
