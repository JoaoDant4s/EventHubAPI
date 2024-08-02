package com.imd.web2.pass.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.imd.web2.pass.model.TicketType;
import com.imd.web2.pass.model.TicketTypeId;
import com.imd.web2.pass.repository.ITicketTypeRepository;
import com.imd.web2.pass.resources.exceptions.DataAlreadyExistsException;
import com.imd.web2.pass.resources.exceptions.NullParameterException;

@Component
public class TicketTypeService implements ITicketTypeService {

    @Autowired
    private ITicketTypeRepository ticketTypeRepository;

    @Autowired
    private IEventService eventService; //replace with EventFeignClient

    @Override
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    @Override
    public List<TicketType> getTicketTypesByEventId(Integer id) throws NullParameterException {
        if (id == null)
            throw new NullParameterException("ID do evento passado é nulo");

        eventService.getByID(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe nenhum evento com esse id"));
        return ticketTypeRepository.findById_EventID(id);
    }

    @Override
    public Optional<TicketType> getTicketTypeById(TicketTypeId id) {
        return ticketTypeRepository.findById(id);
    }

    @Override
    public TicketType saveTicketType(TicketType ticketType) throws DataAlreadyExistsException {
        Optional<TicketType> ticketTypeToSave = getTicketTypeById(ticketType.getId());
        if (ticketTypeToSave.isPresent())
            throw new DataAlreadyExistsException("Já existe um TicketType com o mesmo ID");
        return ticketTypeRepository.save(ticketType);
    }

    @Override
    public void deleteTicketType(TicketTypeId id) {
        ticketTypeRepository.deleteById(id);
    }

    @Override
    public Optional<TicketType> findTicketTypeByNameAndBatchAndEventID(String name, Integer batch, Integer event_id) {
        return ticketTypeRepository.findById_NameAndId_BatchAndId_EventID(name, batch, event_id);
    }
}
