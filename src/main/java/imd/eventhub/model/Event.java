package imd.eventhub.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 300)
    private String description;

    @Column(length = 100)
    private String name;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime initialDate;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime finalDate;

    @Column
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(length = 6)
    private Integer maximumCapacity;

    @Column(length = 100)
    private String address;

    @Column
    private Boolean active = true;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<SubEvent> subEvents;

    public Event(){
        this.subEvents = new ArrayList<>();
    }

    public Event(String description, String name, LocalDateTime initialDate, LocalDateTime finalDate, EventType type, Integer maximumCapacity, String address) {
        this.description = description;
        this.name = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.type = type;
        this.maximumCapacity = maximumCapacity;
        this.address = address;
        this.subEvents = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public EventType getType() {
        return type;
    }

    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public List<SubEvent> getSubEvents() {
        return subEvents;
    }

    public void addSubEvents(SubEvent subEvent) {
        this.subEvents.add(subEvent);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setSubEvents(List<SubEvent> subEvents) {
        this.subEvents = subEvents;
    }

}
