package imd.eventuhub.repository;

import imd.eventuhub.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicketRepository extends JpaRepository<Ticket,Integer> {
}
