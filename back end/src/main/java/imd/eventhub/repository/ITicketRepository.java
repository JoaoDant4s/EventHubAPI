package imd.eventhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Ticket;

public interface ITicketRepository extends JpaRepository<Ticket,Integer> {
}
