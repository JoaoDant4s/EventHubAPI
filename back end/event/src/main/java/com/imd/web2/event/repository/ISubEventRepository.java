package com.imd.web2.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.event.model.SubEvent;
import com.imd.web2.event.model.Event;

public interface ISubEventRepository extends JpaRepository<SubEvent, Integer>{
    List<SubEvent> getAllSubEventsByEventAndActive(Event event, Boolean active);
}
