package imd.eventhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Person;

public interface IPersonRepository extends JpaRepository<Person,Integer> {
}
