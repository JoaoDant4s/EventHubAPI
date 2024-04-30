package imd.eventhub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Attraction")
public class Attraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 255)
    private String description;
    @Column(length = 20)
    private String contact;

    public Attraction() {
    }

    public Attraction(String description, String contact) {
        this.description = description;
        this.contact = contact;
    }
}
