package imd.eventuhub.service.Person;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import imd.eventuhub.repository.IParticipantRepository;
import imd.eventuhub.service.Person.Interfaces.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
