package imd.eventhub.service.Person.Interfaces;

import org.springframework.stereotype.Service;

import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;

import java.util.List;
import java.util.Optional;

@Service
public interface IParticipantService {

    public void save(Participant participant);
    public void delete(Participant participant);
    public Optional<Participant> getById(Integer id);
    public Optional<Person> getByCpf(String cpf);
    public List<Participant> getList();
}
