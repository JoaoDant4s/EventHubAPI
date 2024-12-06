package com.imd.web2.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "Participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name="participantTicket",
            joinColumns=@JoinColumn(name="participantId"),
            inverseJoinColumns=@JoinColumn(name="ticketId"))
    private List<Ticket> ticketList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    @OneToOne(mappedBy = "participant")
    private User user;


    public Participant(){}
    public Participant(CreditCard creditCard){
        this.creditCard = creditCard;
    }
}