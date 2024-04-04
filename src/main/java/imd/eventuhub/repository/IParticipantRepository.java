package imd.eventuhub.repository;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IParticipantRepository extends JpaRepository<Participant,Integer> {
    public Optional<Person> findByCpf(String cpf);
}
