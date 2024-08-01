package com.imd.web2.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.event.model.Event;

public interface IEventRepository extends JpaRepository<Event, Integer>{
    List<Event> findAllEventsByActive(Boolean active);
}
