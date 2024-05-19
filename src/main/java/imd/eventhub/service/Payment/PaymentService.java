package imd.eventhub.service.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import imd.eventhub.model.Payment;
import imd.eventhub.repository.IPaymentRepository;

@Component
public class PaymentService implements IPaymentService{

    @Autowired
    private IPaymentRepository paymentRepository;

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
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
