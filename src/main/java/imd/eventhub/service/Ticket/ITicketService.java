package imd.eventhub.service.Ticket;

import org.springframework.stereotype.Service;

import imd.eventhub.model.Person;
import imd.eventhub.model.Ticket;

import java.util.List;
import java.util.Optional;

@Service
public interface ITicketService {
    public void save(Ticket person);
    public void delete(Ticket person);
    public Optional<Ticket> getById(Integer id);
    public List<Ticket> getList();
}
