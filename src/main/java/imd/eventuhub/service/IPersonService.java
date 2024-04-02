package imd.eventuhub.service;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPersonService {

        public void save(Person person);
        public void delete(Person aluno);
        public Person getPersonById(Integer id);
        public List<Person> getPersonList();
        public List<Participant> getParticipantList();

}
