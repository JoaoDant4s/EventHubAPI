package com.imd.web2.pass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imd.web2.pass.model.Ticket;

public interface ITicketRepository extends JpaRepository<Ticket,Integer> {
}
