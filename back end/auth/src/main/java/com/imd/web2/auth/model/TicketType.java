package com.imd.web2.auth.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;

@Entity
@Table(name = "ticket_type")
public class TicketType {

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "name")),
        @AttributeOverride(name = "batch", column = @Column(name = "batch")),
        @AttributeOverride(name = "eventID", column = @Column(name = "event_id"))
    })
    private TicketTypeId id;

    @Column
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @Column(length = 150)
    private String description;

    public TicketType() {}

    public TicketType(TicketTypeId id, BigDecimal price, String description) {
        this.id = id;
        this.price = price;
        this.description = description;
    }

    public TicketTypeId getId() {
        return id;
    }

    public void setId(TicketTypeId id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
