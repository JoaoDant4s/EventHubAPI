package imd.eventhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.SubEvent;

public interface ISubEventRepository extends JpaRepository<SubEvent, Integer>{
    
}
