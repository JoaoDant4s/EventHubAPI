package com.imd.web2.pass.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.imd.web2.pass.model.Payment;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, UUID> {
}
