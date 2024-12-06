package com.imd.web2.event.repository;

import com.imd.web2.event.model.Event;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IEventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllEventsByActive(Boolean active);
}
