package imd.eventuhub.service.Ticket;

import imd.eventuhub.model.Person;
import imd.eventuhub.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ITicketService {
    public void save(Ticket person);
    public void delete(Ticket person);
    public Optional<Ticket> getById(Integer id);
    public List<Ticket> getList();
}
