package imd.eventhub.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Ticket;
import imd.eventhub.model.TicketDays;
import imd.eventhub.model.TicketDaysId;

public interface ITicketDaysRepository extends JpaRepository<TicketDays, TicketDaysId>{
    TicketDays findById_DayAndId_TicketID(LocalDate day, Integer ticketID);
}
