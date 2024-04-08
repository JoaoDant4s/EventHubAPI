package imd.eventhub.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Participant")
public class Participant extends Person{

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name="participantTicket",
            joinColumns=@JoinColumn(name="participantId"),
            inverseJoinColumns=@JoinColumn(name="ticketId"))
    private List<Ticket> ticketList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    public Participant(){
        super();
    }
    public Participant(String name, String cpf){
        super(name, cpf);
    }
    public Participant(String name, String cpf, String birthDate){
        super(name, cpf, birthDate);
    }

    public Participant(String name, String cpf, String birthDate, CreditCard creditCard){
        super(name, cpf, birthDate);
        this.creditCard = creditCard;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
    public CreditCard getCreditCard() {
        return creditCard;
    }
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
