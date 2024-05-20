package imd.eventhub.service.TicketDays;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import imd.eventhub.exception.DataAlreadyExistsException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Ticket;
import imd.eventhub.model.TicketDays;

@Service
public interface ITicketDaysService {

    List<TicketDays> saveTicketDays(List<TicketDays> ticketDays)
            throws NullParameterException, DataAlreadyExistsException;

    Optional<TicketDays> getTicketDaysById(LocalDateTime day, Integer ticket) throws NullParameterException;

    void deleteTicketDays(LocalDate day, Ticket ticket) throws NullParameterException, NotFoundException;

    List<TicketDays> getTicketDaysByEventIdAndTicketId(Integer eventID, Integer ticketID)
            throws NotFoundException, NullParameterException, InvalidParameterException;
}
