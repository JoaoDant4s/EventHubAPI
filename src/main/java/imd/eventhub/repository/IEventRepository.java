package imd.eventhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Event;

public interface IEventRepository extends JpaRepository<Event, Integer>{
    List<Event> findAllEventsByActive(Boolean active);
}
