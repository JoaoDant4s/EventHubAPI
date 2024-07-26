package com.imd.web2.pass.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imd.web2.pass.model.Ticket;
import com.imd.web2.pass.model.TicketDays;


@Service
public interface ITicketDaysService {

    List<TicketDays> saveTicketDays(List<TicketDays> ticketDays)
            throws NullParameterException, DataAlreadyExistsException;

    Optional<TicketDays> getTicketDaysById(LocalDateTime day, Integer ticket) throws NullParameterException;

    void deleteTicketDays(LocalDate day, Ticket ticket) throws NullParameterException, NotFoundException;

    List<TicketDays> getTicketDaysByEventIdAndTicketId(Integer eventID, Integer ticketID)
            throws NotFoundException, NullParameterException, InvalidParameterException;
}