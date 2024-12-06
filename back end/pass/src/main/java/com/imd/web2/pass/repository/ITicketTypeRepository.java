package com.imd.web2.pass.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.pass.model.TicketType;
import com.imd.web2.pass.model.TicketTypeId;

public interface ITicketTypeRepository extends JpaRepository<TicketType, TicketTypeId>{
    Optional<TicketType> findById_NameAndId_BatchAndId_EventID(String name, Integer batch, Integer event_id);

    List<TicketType> findById_EventID(Integer event_id);


}
