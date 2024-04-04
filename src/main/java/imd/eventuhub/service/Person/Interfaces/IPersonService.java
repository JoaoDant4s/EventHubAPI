package imd.eventuhub.service.Person.Interfaces;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
