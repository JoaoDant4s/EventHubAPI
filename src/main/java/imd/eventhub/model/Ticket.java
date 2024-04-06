package imd.eventhub.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 255)
    private String description;
    @Column(length = 30)
    private String batch;
    @Column
    private float amount;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name="participantTicket",
            joinColumns=@JoinColumn(name="ticketId"),
            inverseJoinColumns=@JoinColumn(name="participantId"))
    private List<Participant> participantList;

    public Ticket() {
    }

    public Ticket(String description, String batch, float amount) {
        this.description = description;
        this.batch = batch;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }
}
