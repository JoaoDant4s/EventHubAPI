package imd.eventhub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    @Column(length = 14)
    private String cpf;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Column(length = 3)
    private Integer age;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    public User() {
    }

    public User(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public User(String name, String cpf, String birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = LocalDate.parse(birthDate);
    }
}
