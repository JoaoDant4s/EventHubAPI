package com.imd.web2.pass.services;

import org.springframework.stereotype.Service;

import com.imd.web2.pass.model.Payment;
import com.imd.web2.pass.model.Ticket;
import com.imd.web2.pass.resources.exceptions.DataAlreadyExistsException;
import com.imd.web2.pass.resources.exceptions.InvalidParameterException;
import com.imd.web2.pass.resources.exceptions.NotFoundException;
import com.imd.web2.pass.resources.exceptions.NullParameterException;

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
