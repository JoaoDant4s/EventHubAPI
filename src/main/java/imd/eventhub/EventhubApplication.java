package imd.eventhub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.Event;
import imd.eventhub.model.EventType;
import imd.eventhub.model.Participant;
import imd.eventhub.model.Ticket;
import imd.eventhub.repository.IEventRepository;
import imd.eventhub.repository.IPersonRepository;
import imd.eventhub.repository.ITicketRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EventhubApplication {


	@Autowired
	IPersonRepository personRepository;
	@Autowired
	ITicketRepository ticketRepository;
	@Autowired
	IEventRepository eventRepository;

	@Bean
	public CommandLineRunner init(){
		return args -> {

			/*
			 * Cadastrando pessoas
			 */

			Participant pessoa1 = new Participant("Hudson", "999.999.999-01");
			Participant pessoa2 = new Participant("João", "999.999.999-02");
			Participant pessoa3 = new Participant("Nathalia", "999.999.999-03");
			Participant pessoa4 = new Participant("Matheus", "999.999.999-04");
			Attraction pessoa5 = new Attraction("Fulano", "999.999.999-05");
			Attraction pessoa6 = new Attraction("Ciclano", "999.999.999-06");

			/*
			 * Cadastrando ingressos
			 */
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			 Event event1 = new Event("Edição 10 anos", "Spotted", LocalDateTime.parse("2024-08-10 20:00:00", formatter), LocalDateTime.parse("2024-08-11 05:00:00", formatter), EventType.Festival, 5000, "Arena das Dunas");
			
			/*
			 * Cadastrando ingressos
			 */


			Ticket ingresso1 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","1º Lote", 100.00f);
			Ticket ingresso2 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","2º Lote", 120.00f);
			Ticket ingresso3 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","3º Lote", 150.00f);


			List<Ticket> ticketList = new ArrayList<>();
			ticketList.add(ingresso1);
			ticketList.add(ingresso2);

			pessoa1.setTicketList(ticketList);



			/*
			 * Persistindo os dados
			 */


			ticketRepository.save(ingresso1);
			ticketRepository.save(ingresso2);
			ticketRepository.save(ingresso3);

			personRepository.save(pessoa1);
			personRepository.save(pessoa2);
			personRepository.save(pessoa3);
			personRepository.save(pessoa4);
			personRepository.save(pessoa5);
			personRepository.save(pessoa6);

			eventRepository.save(event1);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(EventhubApplication.class, args);
	}

}
