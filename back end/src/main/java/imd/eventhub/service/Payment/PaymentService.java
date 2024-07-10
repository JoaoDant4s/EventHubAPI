package imd.eventhub.service.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NoTicketDaysLinkedInTicketException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Payment;
import imd.eventhub.model.Ticket;
import imd.eventhub.model.TicketDays;
import imd.eventhub.repository.IPaymentRepository;
import imd.eventhub.service.Ticket.ITicketService;
import imd.eventhub.service.TicketDays.ITicketDaysService;

@Component
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private ITicketService ticketService; 

    @Autowired
    private ITicketDaysService ticketDaysService;

    @Transactional(readOnly = true)
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Payment> getPaymentById(UUID id) {
        return paymentRepository.findById(id);
    }

    @Transactional
    @Override
    public Payment savePayment(Payment rawPayment, Integer pendingTicketId) throws NotFoundException, NullParameterException, InvalidParameterException, NoTicketDaysLinkedInTicketException{
        Optional<Ticket> ticket = ticketService.getById(pendingTicketId);
        if(ticket.isEmpty()) throw new NotFoundException("Não existe nenhum ticket com o ID informado");
        Ticket pendingTicket = ticket.get();
        List<TicketDays> days = ticketDaysService.getTicketDaysByEventIdAndTicketId(pendingTicket.getTicketType().getId().getEventID(), pendingTicket.getId());
        if(days.isEmpty()) throw new NoTicketDaysLinkedInTicketException("Ticket não foi reservado para nenhum dia");
        BigDecimal totalToPay = pendingTicket.getTicketType().getPrice().multiply(BigDecimal.valueOf(days.size()));
        rawPayment.setDate(LocalDateTime.now());
        rawPayment.setTotalAmount(totalToPay);
        Payment registry = paymentRepository.save(rawPayment);
        ticketService.updateWithPayment(pendingTicket, registry);
        return registry;
    }
}
