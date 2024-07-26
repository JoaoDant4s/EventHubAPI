package com.imd.web2.pass.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.imd.web2.pass.model.Payment;
import com.imd.web2.pass.resources.exceptions.InvalidParameterException;
import com.imd.web2.pass.resources.exceptions.NoTicketDaysLinkedInTicketException;
import com.imd.web2.pass.resources.exceptions.NotFoundException;
import com.imd.web2.pass.resources.exceptions.NullParameterException;


@Service
public interface IPaymentService {
    
    List<Payment> getAllPayments();

    Optional<Payment> getPaymentById(UUID id);

    Payment savePayment(Payment payment, Integer pendingTicketId) throws NotFoundException, NullParameterException, InvalidParameterException, NoTicketDaysLinkedInTicketException;
}
