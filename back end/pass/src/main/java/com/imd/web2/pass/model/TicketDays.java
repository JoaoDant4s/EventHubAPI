package com.imd.web2.pass.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TicketDays")
public class TicketDays {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "day", column = @Column(name = "day")),
            @AttributeOverride(name = "ticketID", column = @Column(name = "ticket_id")),
    })
    private TicketDaysId id;

    public TicketDays(){}

    public TicketDays(TicketDaysId id) {
        this.id = id;
    }

    public TicketDaysId getId() {
        return id;
    }

    public void setId(TicketDaysId id) {
        this.id = id;
    }
    
}
