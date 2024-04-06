package imd.eventhub.service.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.model.Ticket;
import imd.eventhub.repository.ITicketRepository;

import java.util.List;
import java.util.Optional;

@Component
public class TicketService implements ITicketService {
    @Autowired
    ITicketRepository ticketRepository;

    @Override
    public void save(Ticket ticket){
        ticketRepository.save(ticket);
    }

    @Override
    public void delete(Ticket ticket){
        ticketRepository.delete(ticket);
    }

    @Override
    public Optional<Ticket> getById(Integer id){
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> getList(){
        return ticketRepository.findAll();
    }
}
