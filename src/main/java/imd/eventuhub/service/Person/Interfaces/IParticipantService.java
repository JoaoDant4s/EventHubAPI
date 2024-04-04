package imd.eventuhub.service.Person.Interfaces;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IParticipantService {

    public void save(Participant participant);
    public void delete(Participant participant);
    public Optional<Participant> getById(Integer id);
    public Optional<Person> getByCpf(String cpf);
    public List<Participant> getList();
}
