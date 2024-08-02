package com.imd.web2.event.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imd.web2.event.model.Event;
import com.imd.web2.event.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.event.resources.exceptions.InvalidParameterException;
import com.imd.web2.event.resources.exceptions.NullParameterException;

@Service
public interface IEventService {
    public Boolean isValid(Event event) throws Exception;
    public Event save(Event event) throws NullParameterException, InvalidParameterException, DateOutOfRangeException;
    public void deactivate(Event event) throws Exception;
    public Optional<Event> getByID(Integer id) throws NullParameterException;
    public List<Event> getList();
}
