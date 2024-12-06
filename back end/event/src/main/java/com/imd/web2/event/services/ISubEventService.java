package com.imd.web2.event.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imd.web2.event.model.SubEvent;
import com.imd.web2.event.resources.exceptions.DateOutOfRangeException;
import com.imd.web2.event.resources.exceptions.InvalidParameterException;
import com.imd.web2.event.resources.exceptions.NotFoundException;
import com.imd.web2.event.resources.exceptions.NullParameterException;


@Service
public interface ISubEventService {
    public Boolean isValid(SubEvent event) throws Exception;

    public SubEvent save(SubEvent event)
            throws NotFoundException, NullParameterException, InvalidParameterException, DateOutOfRangeException;

    public void deactivate(SubEvent event) throws Exception;

    public Optional<SubEvent> getByID(Integer id) throws NullParameterException;

    public List<SubEvent> getList();

    public List<SubEvent> getListByEventid(Integer id) throws NotFoundException, NullParameterException;
}
