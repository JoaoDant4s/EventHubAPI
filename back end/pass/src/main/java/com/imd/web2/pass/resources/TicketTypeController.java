package com.imd.web2.pass.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import imd.eventhub.exception.DataAlreadyExistsException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Event;
import imd.eventhub.model.TicketType;
import imd.eventhub.model.TicketTypeId;
import imd.eventhub.restAPI.dto.ticketType.TicketTypeDTO;
import imd.eventhub.service.Event.IEventService;
import imd.eventhub.service.TicketType.ITicketTypeService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/ticketType")
public class TicketTypeController {
    
    @Autowired
    ITicketTypeService ticketTypeService;

    @Autowired 
    IEventService eventService;

    @PostMapping("")
    public TicketTypeDTO saveTicketType(@Valid @RequestBody TicketTypeDTO ticketTypeDTO) {
        try{
            TicketType ticketTypeSaved = ticketTypeService.saveTicketType(fromDto(ticketTypeDTO));
            return toDto(ticketTypeSaved);
        } catch (DataAlreadyExistsException | NullParameterException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public TicketTypeDTO getTicketTypeById(@PathVariable TicketTypeId id) {
        try {
            TicketType ticketType = ticketTypeService.getTicketTypeById(id)
                    .orElseThrow(() -> new NotFoundException("Tipo de ingresso não encontrado"));
            return toDto(ticketType);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/list/{eventId}")
    public List<TicketTypeDTO> getTicketTypesByEventId(@PathVariable Integer eventId) {
        try {
            List<TicketType> ticketTypes = ticketTypeService.getTicketTypesByEventId(eventId);
            return ticketTypes.stream()
                    .map(TicketTypeController::toDto)
                    .collect(Collectors.toList());
        } catch (NotFoundException | NullParameterException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public static TicketTypeDTO toDto(TicketType ticketType) {
        TicketTypeDTO dto = new TicketTypeDTO();
        dto.setName(ticketType.getId().getName());
        dto.setBatch(ticketType.getId().getBatch());
        dto.setPrice(ticketType.getPrice());
        dto.setDescription(ticketType.getDescription());
        dto.setEventID(ticketType.getId().getEventID());
        return dto;
    }

    private TicketType fromDto(TicketTypeDTO dto) throws NotFoundException, NullParameterException {
        TicketType ticketType = new TicketType();
        Event event = eventService.getByID(dto.getEventID()).orElseThrow(() -> new NotFoundException("Evento não encontrado"));
        TicketTypeId ticketTypeId = new TicketTypeId(dto.getName(), dto.getBatch(), event.getId());
        ticketType.setId(ticketTypeId);
        ticketType.setPrice(dto.getPrice());
        ticketType.setDescription(dto.getDescription());
        
        return ticketType;
    }
}
