package com.imd.web2.pass.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.pass.model.TicketDays;
import com.imd.web2.pass.model.TicketDaysId;

public interface ITicketDaysRepository extends JpaRepository<TicketDays, TicketDaysId>{
    TicketDays findById_DayAndId_TicketID(LocalDate day, Integer ticketID);
}
