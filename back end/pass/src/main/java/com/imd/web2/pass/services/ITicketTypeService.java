package com.imd.web2.pass.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import imd.eventhub.exception.DataAlreadyExistsException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.TicketType;
import imd.eventhub.model.TicketTypeId;

@Service
public interface ITicketTypeService {

    List<TicketType> getAllTicketTypes();

    Optional<TicketType> getTicketTypeById(TicketTypeId id);

    List<TicketType> getTicketTypesByEventId(Integer id) throws NullParameterException;

    TicketType saveTicketType(TicketType ticketType) throws DataAlreadyExistsException;

    void deleteTicketType(TicketTypeId id);

    Optional<TicketType> findTicketTypeByNameAndBatchAndEventID(String name, Integer batch, Integer event_id) ;
}
