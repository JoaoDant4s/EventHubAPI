package com.imd.web2.pass.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.imd.web2.pass.model.Ticket;
import com.imd.web2.pass.model.TicketDays;
import com.imd.web2.pass.model.TicketDaysId;

// import imd.eventhub.exception.DataAlreadyExistsException;
// import imd.eventhub.exception.InvalidParameterException;
// import imd.eventhub.exception.NotFoundException;
// import imd.eventhub.exception.NullParameterException;
import imd.eventhub.repository.ITicketRepository;
import imd.eventhub.service.Event.IEventService;
import imd.eventhub.service.Participant.IParticipantService;
import imd.eventhub.service.TicketDays.ITicketDaysService;
import imd.eventhub.service.TicketType.ITicketTypeService;

@Component
public class TicketService implements ITicketService {
    @Autowired
    ITicketRepository ticketRepository;
    @Autowired
    IParticipantService participantService;
    @Autowired
    ITicketTypeService ticketTypeService;
    @Autowired
    ITicketDaysService ticketDaysService;
    @Autowired
    IEventService eventService;

    @Override
    public Ticket save(Ticket ticket, List<LocalDate> days)
            throws NullParameterException, NotFoundException, InvalidParameterException, DataAlreadyExistsException {
        if (ticket == null || days == null || ticket.getParticipant() == null || ticket.getTicketType() == null) {
            throw new NullParameterException("Ticket ou dias não pode ser nulo.");
        }
        if (days.isEmpty()) {
            throw new InvalidParameterException("Número de dias não pode ser 0");
        }
        Event event = eventService.getByID(ticket.getTicketType().getId().getEventID()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe nenhum evento com esse id"));
        LocalDate initialDate = event.getInitialDate().toLocalDate();
        LocalDate finalDate = event.getFinalDate().toLocalDate();
        if (days.stream().anyMatch(day -> day.isBefore(initialDate) || day.isAfter(finalDate))) {
            throw new InvalidParameterException("Algum dos dias passados não faz parte dos dias que o evento ocorrerá");
        }

        final Ticket ticketSaved = ticketRepository.save(ticket);

        List<TicketDays> ticketDaysList = days.stream()
                .map(day -> {
                    TicketDays ticketDays = new TicketDays();
                    ticketDays.setId(new TicketDaysId(day, ticketSaved.getId()));
                    return ticketDays;
                })
                .collect(Collectors.toList());

        ticketDaysService.saveTicketDays(ticketDaysList);
        return ticket;
    }

    @Override
    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    @Override
    public Optional<Ticket> getById(Integer id) throws NotFoundException {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> getList() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket updateWithPayment(Ticket paidTicket, Payment registry) throws NullParameterException{
        paidTicket.setPayment(registry);
        return ticketRepository.save(paidTicket);
    }
}
