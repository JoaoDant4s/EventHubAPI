package imd.eventhub;

import imd.eventhub.model.*;
import imd.eventhub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.Event;
import imd.eventhub.model.EventType;
import imd.eventhub.model.Participant;
import imd.eventhub.model.SubEvent;
import imd.eventhub.model.Ticket;
import imd.eventhub.repository.ISubEventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EventhubApplication {


	@Autowired
	IUserRepository userRepository;
	@Autowired
	IParticipantRepository participantRepository;
	@Autowired
	IPersonRepository personRepository;
	@Autowired
	ITicketRepository ticketRepository;
	@Autowired
	IEventRepository eventRepository;
	@Autowired
	ISubEventRepository subEventRepository;

	@Bean
	public CommandLineRunner init(){
		return args -> {

			/*
			 * Cadastrando pessoas
			 */

			Participant person1 = new Participant("Hudson", "999.999.999-01");
			Participant person2 = new Participant("João", "999.999.999-02");
			Participant person3 = new Participant("Nathalia", "999.999.999-03");
			Participant person4 = new Participant("Matheus", "999.999.999-04");
			Participant person5 = new Participant("Fulano", "999.999.999-05");
			Participant person6 = new Participant("Ciclano", "999.999.999-06");

			User user1 = new User("Hudson", "999.999.999-01");
			User user2 = new User("João", "999.999.999-02");
			User user3 = new User("Nathalia", "999.999.999-03");
			User user4 = new User("Matheus", "999.999.999-04");
			User user5 = new User("Fulano", "999.999.999-05");
			User user6 = new User("Ciclano", "999.999.999-06");

			/*
			 * Cadastrando eventos
			 */
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			 Event event1 = new Event("Edição 10 anos", "Spotted", LocalDateTime.parse("2024-08-10 20:00:00", formatter), LocalDateTime.parse("2024-08-11 05:00:00", formatter), EventType.Festival, 5000, "Arena das Dunas");

			 Event event2 = new Event("go rn", "GO-RN", LocalDateTime.parse("2024-06-30 12:00:00", formatter), LocalDateTime.parse("2024-07-02 12:00:00", formatter), EventType.Talk, 1000, "IMD");

			/*
			 * Cadastrando subeventos
			 */
			SubEvent subEvent1 = new SubEvent(EventType.Show, LocalDateTime.parse("2024-06-30 12:00:00", formatter), "Major RD", "10 anos", "Palco Trap", event1);
			SubEvent subEvent2 = new SubEvent(EventType.Show, LocalDateTime.parse("2024-08-10 20:00:00", formatter), "MC Loma e As Gêmeas Lacração" ,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "Setor A", event1);
			SubEvent subEvent3 = new SubEvent(EventType.Workshop, LocalDateTime.parse("2024-08-10 20:00:00", formatter), "Workshop Spring" ,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "Setor A", event2);
			SubEvent subEvent4 = new SubEvent(EventType.Workshop, LocalDateTime.parse("2024-08-10 20:00:00", formatter), "Workshop React" ,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "Setor B", event2);
			SubEvent subEvent5 = new SubEvent(EventType.Talk, LocalDateTime.parse("2024-08-10 20:00:00", formatter), "Empreendedorismo no RN" ,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "Setor C", event2);
			/*
			 * Cadastrando ingressos
			 */

			List<SubEvent> subEventList1 = new ArrayList<>();
			subEventList1.add(subEvent3);
			subEventList1.add(subEvent4);
			subEventList1.add(subEvent5);

			List<SubEvent> subEventList2 = new ArrayList<>();
			subEventList2.add(subEvent3);

			List<SubEvent> subEventList3 = new ArrayList<>();
			subEventList3.add(subEvent4);
			subEventList3.add(subEvent5);

			Ticket ingresso1 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","1º Lote", 100.00f);
			Ticket ingresso2 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","2º Lote", 120.00f);
			Ticket ingresso3 = new Ticket("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","3º Lote", 150.00f);


			List<Ticket> ticketList = new ArrayList<>();
			ticketList.add(ingresso1);
			ticketList.add(ingresso2);




			/*
			 * Persistindo os dados
			 */


			ticketRepository.save(ingresso1);
			ticketRepository.save(ingresso2);
			ticketRepository.save(ingresso3);

			participantRepository.save(person1);
			participantRepository.save(person2);
			participantRepository.save(person3);
			participantRepository.save(person4);
			participantRepository.save(person5);
			participantRepository.save(person6);

			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			userRepository.save(user4);
			userRepository.save(user5);
			userRepository.save(user6);

			eventRepository.save(event1);
			eventRepository.save(event2);

			subEventRepository.save(subEvent1);
			subEventRepository.save(subEvent2);
			subEventRepository.save(subEvent3);
			subEventRepository.save(subEvent4);
			subEventRepository.save(subEvent5);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(EventhubApplication.class, args);
	}

}
