package imd.eventuhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventuhub.model.Event;

public interface IEventRepository extends JpaRepository<Event, Integer>{
    
}
