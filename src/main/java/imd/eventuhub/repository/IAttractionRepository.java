package imd.eventuhub.repository;

import imd.eventuhub.model.Attraction;
import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAttractionRepository extends JpaRepository<Attraction,Integer> {
    public Optional<Person> findByCpf(String cpf);
}
