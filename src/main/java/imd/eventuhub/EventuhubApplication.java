package imd.eventuhub;

import imd.eventuhub.model.Attraction;
import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import imd.eventuhub.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventuhubApplication {


	@Autowired
	IPersonRepository personRepository;

	@Bean
	public CommandLineRunner init(){
		return args -> {

			/*
			 * Cadastrando pessoas
			 */

			Person pessoa1 = new Participant("Hudson", "999.999.999-01");
			Person pessoa2 = new Participant("João", "999.999.999-02");
			Person pessoa3 = new Participant("Nathalia", "999.999.999-03");
			Person pessoa4 = new Participant("Matheus", "999.999.999-04");
			Person pessoa5 = new Attraction("Fulano", "999.999.999-05");

			personRepository.save(pessoa1);
			personRepository.save(pessoa2);
			personRepository.save(pessoa3);
			personRepository.save(pessoa4);
			personRepository.save(pessoa5);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(EventuhubApplication.class, args);
	}

}
