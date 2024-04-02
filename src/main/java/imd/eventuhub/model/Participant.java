package imd.eventuhub.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Participant")
public class Participant extends Person{

    public Participant(){
        super();
    }
    public Participant(String name, String cpf){
        super(name, cpf);
    }
}
