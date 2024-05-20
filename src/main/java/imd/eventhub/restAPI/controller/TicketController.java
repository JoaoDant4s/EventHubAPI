package imd.eventhub.restAPI.controller;


import imd.eventhub.exception.DataAlreadyExistsException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Participant;
import imd.eventhub.model.Payment;
import imd.eventhub.model.Ticket;
import imd.eventhub.model.TicketType;
import imd.eventhub.restAPI.dto.ticket.SaveTicketDTO;
import imd.eventhub.restAPI.dto.ticket.TicketDTO;
import imd.eventhub.service.Participant.IParticipantService;
import imd.eventhub.service.Ticket.ITicketService;
import imd.eventhub.service.TicketType.ITicketTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/ticket")
public class TicketController {

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private ITicketTypeService ticketTypeService;

    @Autowired
    private IParticipantService participantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDTO saveTicket(@Valid @RequestBody SaveTicketDTO saveTicketDTO) {
        try {
            Ticket ticket;
            ticket = ticketService.save(fromSaveDTO(saveTicketDTO), saveTicketDTO.getDays());
            return toDTO(ticket);
        } catch (NullParameterException | DataAlreadyExistsException | InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{ticketId}")
    public TicketDTO getTicket(@PathVariable Integer ticketId) {
        try {
            Ticket ticket = ticketService.getById(ticketId).orElseThrow(() -> new NotFoundException("Nenhum ticket encontrado"));
            return toDTO(ticket);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private Ticket fromSaveDTO(SaveTicketDTO dto){
        Ticket ticket = new Ticket();
        try {
            TicketType ticketType = ticketTypeService.findTicketTypeByNameAndBatchAndEventID(dto.getTicketTypeId().getName(), dto.getTicketTypeId().getBatch(), dto.getTicketTypeId().getEventID()).orElseThrow(() -> new NotFoundException("Não existe um TicketType com esse ID"));
            ticket.setTicketType(ticketType);
            Participant participant = participantService.getParticipantById(dto.getParticipantId()).orElseThrow(() -> new NotFoundException("Não existe um TicketType com esse ID"));
            ticket.setParticipant(participant);
            return ticket;
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setPayment(ticket.getPayment());
        dto.setPaymentStatus(dto.getPayment() == null ? "PENDENTE" : "PAGO");
        dto.setTicketTypeDTO(TicketTypeController.toDto(ticket.getTicketType()));
        return dto;
    }
}
