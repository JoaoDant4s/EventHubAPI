package com.imd.web2.event.model;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Embeddable;

@Embeddable
public class TicketDaysId implements Serializable {

    private LocalDate day;

    private Integer ticketID;

    public TicketDaysId(){}

    public TicketDaysId(LocalDate day, Integer ticketID) {
        this.day = day;
        this.ticketID = ticketID;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public void setTicketID(Integer ticketID) {
        this.ticketID = ticketID;
    }

}
