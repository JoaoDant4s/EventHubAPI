package imd.eventhub.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonRepository extends JpaRepository<Person,Integer> {
}
