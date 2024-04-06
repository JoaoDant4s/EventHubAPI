package imd.eventuhub.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "SubEvent")
public class SubEvent {

    @Column
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dayHour;

    @Column(length = 300)
    private String description;


    @Column(length = 150)
    private String location;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    
    public EventType getType() {
        return type;
    }


    public void setType(EventType type) {
        this.type = type;
    }


    public LocalDateTime getDayHour() {
        return dayHour;
    }


    public void setDayHour(LocalDateTime dayHour) {
        this.dayHour = dayHour;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public Event getEvent() {
        return event;
    }


    public void setEventId(Event event) {
        this.event = event;
    }
}
