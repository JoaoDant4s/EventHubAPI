package com.imd.web2.pass.services;

import org.springframework.stereotype.Service;

import com.imd.web2.pass.model.Ticket;

import imd.eventhub.exception.DataAlreadyExistsException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface ITicketService {
    public Ticket save(Ticket ticket, List<LocalDate> days) throws NullParameterException, NotFoundException, InvalidParameterException, DataAlreadyExistsException;
    public Ticket updateWithPayment(Ticket ticket, Payment registry) throws NullParameterException;
    public void delete(Ticket person);
    public Optional<Ticket> getById(Integer id) throws NotFoundException;
    public List<Ticket> getList();
}
