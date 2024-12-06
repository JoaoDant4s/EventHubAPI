package com.imd.web2.event.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class TicketTypeId implements Serializable {
    private String name;
    private Integer batch;
    private Integer eventID;

    public TicketTypeId() {}

    public TicketTypeId(String name, Integer batch, Integer eventID) {
        this.name = name;
        this.batch = batch;
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }
}
