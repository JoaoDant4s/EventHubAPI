package imd.eventhub.service.TicketDays;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import imd.eventhub.exception.DataAlreadyExistsException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Ticket;
import imd.eventhub.model.TicketDays;
import imd.eventhub.model.TicketDaysId;
import imd.eventhub.repository.ITicketDaysRepository;

@Component
public class TicketDaysService implements ITicketDaysService{

    @Autowired
    private ITicketDaysRepository ticketDaysRepository;

    @Override
    public List<TicketDays> saveTicketDays(List<TicketDays> ticketDays) throws NullParameterException, DataAlreadyExistsException{
        if (ticketDays.isEmpty() || ticketDays == null) {
            throw new NullParameterException("Deve haver tickets para serem salvos");
        }
        if (ticketDays.stream().anyMatch(td -> ticketDaysRepository.findById(td.getId()).isPresent())) {
            throw new DataAlreadyExistsException("Já existe um registro de TicketDays para o mesmo dia e ticket.");
        }
    
        return ticketDaysRepository.saveAll(ticketDays);
    }

    @Override
    public Optional<TicketDays> getTicketDaysById(LocalDate day, Ticket ticket) throws NullParameterException{
        if (day == null || ticket == null) {
            throw new NullParameterException("Data ou ticket não podem ser nulos.");
        }
        return ticketDaysRepository.findById(new TicketDaysId(day, ticket.getId()));
    }

    @Override
    public void deleteTicketDays(LocalDate day, Ticket ticket) throws NullParameterException, NotFoundException{
        if (day == null || ticket == null) {
            throw new NullParameterException("Data ou ticket não podem ser nulos.");
        }
        if (ticketDaysRepository.findById(new TicketDaysId(day, ticket.getId())).isEmpty()) {
            throw new NotFoundException("Não foi encontrado nenhum TicketDay com os dados informados");
        }
        ticketDaysRepository.deleteById(new TicketDaysId(day, ticket.getId()));
    }
}
