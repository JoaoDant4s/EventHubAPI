package imd.eventhub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Attraction")
public class Attraction extends Person {

    @Column(length = 255)
    private String description;
    @Column(length = 20)
    private String contact;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Attraction() {
        super();
    }

    public Attraction(String name, String cpf) {
        super(name, cpf);
    }

    public Attraction(String name, String cpf, String birthDate){
        super(name, cpf, birthDate);
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
