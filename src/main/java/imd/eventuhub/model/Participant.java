package imd.eventuhub.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Participant")
public class Participant extends Person{

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name="participantTicket",
            joinColumns=@JoinColumn(name="participantId"),
            inverseJoinColumns=@JoinColumn(name="ticketId"))
    private List<Ticket> ticketList;

    public Participant(){
        super();
    }
    public Participant(String name, String cpf){
        super(name, cpf);
    }
    public Participant(String name, String cpf, String birthDate){
        super(name, cpf, birthDate);
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
