package imd.eventhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Event;

public interface IEventRepository extends JpaRepository<Event, Integer>{

}
