package imd.eventuhub.repository;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPersonRepository extends JpaRepository<Person,Integer> {
}
