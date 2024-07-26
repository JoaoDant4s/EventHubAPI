package com.imd.web2.pass.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.imd.web2.pass.model.Payment;

import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NoTicketDaysLinkedInTicketException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;

@Service
public interface IPaymentService {
    
    List<Payment> getAllPayments();

    Optional<Payment> getPaymentById(UUID id);

    Payment savePayment(Payment payment, Integer pendingTicketId) throws NotFoundException, NullParameterException, InvalidParameterException, NoTicketDaysLinkedInTicketException;
}
