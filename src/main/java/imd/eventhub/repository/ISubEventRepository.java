package imd.eventhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Event;
import imd.eventhub.model.SubEvent;

public interface ISubEventRepository extends JpaRepository<SubEvent, Integer>{
    List<SubEvent> getAllActiveSubEventsByEvent(Event event);
}
