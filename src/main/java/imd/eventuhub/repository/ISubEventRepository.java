package imd.eventuhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventuhub.model.SubEvent;

public interface ISubEventRepository extends JpaRepository<SubEvent, Integer>{
    
}
