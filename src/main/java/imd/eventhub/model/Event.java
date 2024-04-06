package imd.eventhub.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime initialDate;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime finalDate;

    @Column
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(length = 6)
    private Integer lotacaoMaxima;

    @Column(length = 100)
    private String address;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<SubEvent> subEvents;

    public Event(){
        this.subEvents = new ArrayList<>();
    }

    public Event(Integer id, String description, String name, LocalDateTime initialDate, LocalDateTime finalDate, EventType type, Integer lotacaoMaxima, String address, List<SubEvent> subEvents) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.type = type;
        this.lotacaoMaxima = lotacaoMaxima;
        this.address = address;
        this.subEvents = subEvents;
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

    public Integer getLotacaoMaxima() {
        return lotacaoMaxima;
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

    public void setLotacaoMaxima(Integer lotacaoMaxima) {
        this.lotacaoMaxima = lotacaoMaxima;
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

}
