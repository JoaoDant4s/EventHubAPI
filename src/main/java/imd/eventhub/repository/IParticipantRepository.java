package imd.eventhub.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;

import java.util.Optional;


public interface IParticipantRepository extends JpaRepository<Participant,Integer> {
    public Optional<Person> findByCpf(String cpf);
}
