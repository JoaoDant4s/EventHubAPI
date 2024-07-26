package com.imd.web2.pass.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imd.web2.pass.model.TicketType;
import com.imd.web2.pass.model.TicketTypeId;
import com.imd.web2.pass.resources.exceptions.DataAlreadyExistsException;
import com.imd.web2.pass.resources.exceptions.NullParameterException;

@Service
public interface ITicketTypeService {

    List<TicketType> getAllTicketTypes();

    Optional<TicketType> getTicketTypeById(TicketTypeId id);

    List<TicketType> getTicketTypesByEventId(Integer id) throws NullParameterException;

    TicketType saveTicketType(TicketType ticketType) throws DataAlreadyExistsException;

    void deleteTicketType(TicketTypeId id);

    Optional<TicketType> findTicketTypeByNameAndBatchAndEventID(String name, Integer batch, Integer event_id) ;
}
