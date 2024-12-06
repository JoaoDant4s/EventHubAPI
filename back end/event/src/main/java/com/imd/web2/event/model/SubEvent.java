package com.imd.web2.event.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "SubEvent")
public class SubEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime hours;

    @Column(length = 300)
    private String description;

    @Column(length = 150)
    private String location;

    @Column
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    
    public SubEvent() {}

    public SubEvent(Event event) {
        this.event = event;
    }

    public SubEvent(EventType type, LocalDateTime hours, String name ,String description, String location, Event event) {
		this.type = type;
		this.hours = hours;
        this.name = name;
		this.description = description;
		this.location = location;
		this.event = event;
	}


	public EventType getType() {
        return type;
    }


    public void setType(EventType type) {
        this.type = type;
    }


    public LocalDateTime getHours() {
        return hours;
    }


    public void setHours(LocalDateTime hours) {
        this.hours = hours;
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


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setEvent(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
