package imd.eventhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;

import java.util.Optional;

public interface IAttractionRepository extends JpaRepository<Attraction,Integer> {
    public Optional<Person> findByCpf(String cpf);
}
