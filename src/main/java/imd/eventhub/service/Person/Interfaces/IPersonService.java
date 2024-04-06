package imd.eventhub.service.Person.Interfaces;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;

import java.util.List;
import java.util.Optional;

@Service
public interface IPersonService {

        public void save(Person person);
        public void delete(Person person);
        public Person getById(Integer id);
        public Optional<Person> getByCpf(String cpf);
        public List<Person> getList();

}
