package imd.eventuhub;

import imd.eventuhub.model.Attraction;
import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import imd.eventuhub.model.Ticket;
import imd.eventuhub.repository.IPersonRepository;
import imd.eventuhub.repository.ITicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventuhubApplication {


	@Autowired
	IPersonRepository personRepository;
	@Autowired
	ITicketRepository ticketRepository;

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

			/*
			 * Cadastrando ingressos
			 */


			Ticket ingresso1 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","1º Lote", 100.00f);
			Ticket ingresso2 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","2º Lote", 120.00f);
			Ticket ingresso3 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","3º Lote", 150.00f);

			ticketRepository.save(ingresso1);
			ticketRepository.save(ingresso2);
			ticketRepository.save(ingresso3);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(EventuhubApplication.class, args);
	}

}
