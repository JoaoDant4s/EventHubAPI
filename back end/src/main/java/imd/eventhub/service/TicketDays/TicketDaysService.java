package imd.eventhub.service.TicketDays;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.exception.DataAlreadyExistsException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Event;
import imd.eventhub.model.Ticket;
import imd.eventhub.model.TicketDays;
import imd.eventhub.model.TicketDaysId;
import imd.eventhub.repository.ITicketDaysRepository;
import imd.eventhub.service.Event.IEventService;

@Component
public class TicketDaysService implements ITicketDaysService {

    @Autowired
    private ITicketDaysRepository ticketDaysRepository;

    @Autowired
    private IEventService eventService;

    @Override
    public List<TicketDays> saveTicketDays(List<TicketDays> ticketDays)
            throws NullParameterException, DataAlreadyExistsException {
        if (ticketDays.isEmpty() || ticketDays == null) {
            throw new NullParameterException("Deve haver tickets para serem salvos");
        }
        if (ticketDays.stream().anyMatch(td -> ticketDaysRepository.findById(td.getId()).isPresent())) {
            throw new DataAlreadyExistsException("Já existe um registro de TicketDays para o mesmo dia e ticket.");
        }

        return ticketDaysRepository.saveAll(ticketDays);
    }

    @Override
    public Optional<TicketDays> getTicketDaysById(LocalDateTime day, Integer ticket) throws NullParameterException {
        if (day == null || ticket == null) {
            throw new NullParameterException("Data ou ticket não podem ser nulos.");
        }
        return ticketDaysRepository.findById(new TicketDaysId(day.toLocalDate(), ticket));
    }

    @Override
    public List<TicketDays> getTicketDaysByEventIdAndTicketId(Integer eventID, Integer ticketID)
            throws NotFoundException, NullParameterException, InvalidParameterException {
        Optional<Event> optEvent = eventService.getByID(eventID);
        if (optEvent.isEmpty())
            throw new NotFoundException("Não existe nenhum evento com o ID informado");
        Event event = optEvent.get();
        List<LocalDateTime> eventDays = getDateTimeRange(event.getInitialDate(), event.getFinalDate());
        return getTicketDaysByEventDays(eventDays, ticketID);
    }

    private List<TicketDays> getTicketDaysByEventDays(List<LocalDateTime> eventDays, Integer ticketID) {
        return eventDays.stream()
                        .map(day -> {
                            try {
                                return getTicketDaysById(day, ticketID);
                            } catch (NullParameterException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
    }

    private List<LocalDateTime> getDateTimeRange(LocalDateTime start, LocalDateTime end)
            throws InvalidParameterException {
        List<LocalDateTime> dateTimeRange = new ArrayList<>();

        if (start.isAfter(end)) {
            throw new InvalidParameterException("A data do início deve vir antes da data final do evento");
        }

        LocalDateTime current = start;
        while (!current.isAfter(end)) {
            dateTimeRange.add(current);
            current = current.plusDays(1);
        }

        return dateTimeRange;
    }

    @Override
    public void deleteTicketDays(LocalDate day, Ticket ticket) throws NullParameterException, NotFoundException {
        if (day == null || ticket == null) {
            throw new NullParameterException("Data ou ticket não podem ser nulos.");
        }
        if (ticketDaysRepository.findById(new TicketDaysId(day, ticket.getId())).isEmpty()) {
            throw new NotFoundException("Não foi encontrado nenhum TicketDay com os dados informados");
        }
        ticketDaysRepository.deleteById(new TicketDaysId(day, ticket.getId()));
    }
}
