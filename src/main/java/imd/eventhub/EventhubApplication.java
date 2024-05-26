package imd.eventhub;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.Event;
import imd.eventhub.model.EventType;
import imd.eventhub.model.Participant;
import imd.eventhub.model.SubEvent;
import imd.eventhub.model.TicketType;
import imd.eventhub.model.TicketTypeId;
import imd.eventhub.model.User;
import imd.eventhub.repository.IAttractionRepository;
import imd.eventhub.repository.IEventRepository;
import imd.eventhub.repository.IParticipantRepository;
import imd.eventhub.repository.ISubEventRepository;
import imd.eventhub.repository.ITicketRepository;
import imd.eventhub.repository.ITicketTypeRepository;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.service.User.IUserService;

@SpringBootApplication
public class EventhubApplication {

	@Autowired
	IUserRepository userRepository;
	@Autowired
	IUserService userService;
	@Autowired
	IParticipantRepository participantRepository;
	@Autowired
	IAttractionRepository attractionRepository;
	@Autowired
	ITicketRepository ticketRepository;
	@Autowired
	IEventRepository eventRepository;
	@Autowired
	ISubEventRepository subEventRepository;
	@Autowired
	ITicketTypeRepository ticketTypeRepository;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner init() {
		return args -> {

			/*
			 * Cadastrando pessoas
			 */

			User user1 = new User("Admin", "999.999.999-01", "1990-02-23", "admin", passwordEncoder.encode("123456789"), true, false);
			User user2 = new User("Promoter", "999.999.999-02", "1982-10-11", "promoter", passwordEncoder.encode("123456789"), false, true);
			User user3 = new User("Hudson", "999.999.999-03", "2001-09-12", "user1@gmail.com", passwordEncoder.encode("123456789"), false, false);
			User user4 = new User("João", "999.999.999-04", "2000-10-20", "joao@gmail.com", passwordEncoder.encode("123"), false, false);
			User user5 = new User("Nathalia", "999.999.999-05", "2000-08-31", "user3@gmail.com", passwordEncoder.encode("123456789"), false, false);
			User user6 = new User("Matheus", "999.999.999-06", "1996-02-10", "user4@gmail.com", passwordEncoder.encode("123456789"), false, false);
			User user7 = new User("Atração", "999.999.999-07", "1989-11-02", "user5@gmail.com", passwordEncoder.encode("123456789"), false, false);

			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			userRepository.save(user4);
			userRepository.save(user5);
			userRepository.save(user6);
			userRepository.save(user7);

			Participant participant1 = new Participant();
			Participant participant2 = new Participant();
			Participant participant3 = new Participant();
			Participant participant4 = new Participant();
			Participant participant5 = new Participant();
			Participant participant6 = new Participant();
			Participant participant7 = new Participant();

			participantRepository.save(participant1);
			participantRepository.save(participant2);
			participantRepository.save(participant3);
			participantRepository.save(participant4);
			participantRepository.save(participant5);
			participantRepository.save(participant6);
			participantRepository.save(participant7);

			Attraction attraction1 = new Attraction("Lorem ipsum","(84) 99898-4545");

			attractionRepository.save(attraction1);

			userService.setUserParticipant(1,1);
			userService.setUserParticipant(2,2);
			userService.setUserParticipant(3,3);
			userService.setUserParticipant(4,4);
			userService.setUserParticipant(5,5);
			userService.setUserParticipant(6,6);
			userService.setUserParticipant(7,7);
			userService.setUserAttraction(7,1);

			/*
			 * Cadastrando eventos
			 */
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			Event event1 = new Event("Edição 10 anos", "Spotted", LocalDateTime.parse("2024-08-10 20:00:00", formatter),
					LocalDateTime.parse("2024-08-11 05:00:00", formatter), EventType.Festival, 5000, "Arena das Dunas");

			Event event2 = new Event("go rn", "GO-RN", LocalDateTime.parse("2024-06-30 12:00:00", formatter),
					LocalDateTime.parse("2024-07-02 12:00:00", formatter), EventType.Talk, 1000, "IMD");
			/*
			 * Cadastrando subeventos
			 */
			SubEvent subEvent1 = new SubEvent(EventType.Show, LocalDateTime.parse("2024-06-30 12:00:00", formatter),
					"Major RD", "10 anos", "Palco Trap", event1);
			SubEvent subEvent2 = new SubEvent(EventType.Show, LocalDateTime.parse("2024-08-10 20:00:00", formatter),
					"MC Loma e As Gêmeas Lacração",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
					"Setor A", event1);
			SubEvent subEvent3 = new SubEvent(EventType.Workshop, LocalDateTime.parse("2024-08-10 20:00:00", formatter),
					"Workshop Spring",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
					"Setor A", event2);
			SubEvent subEvent4 = new SubEvent(EventType.Workshop, LocalDateTime.parse("2024-08-10 20:00:00", formatter),
					"Workshop React",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
					"Setor B", event2);
			SubEvent subEvent5 = new SubEvent(EventType.Talk, LocalDateTime.parse("2024-08-10 20:00:00", formatter),
					"Empreendedorismo no RN",
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
					"Setor C", event2);
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

			// Ticket ingresso1 = new Ticket(
			// "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
			// tempor incididunt ut labore et dolore magna aliqua.",
			// "1º Lote", 100.00f);
			// Ticket ingresso2 = new Ticket(
			// "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
			// tempor incididunt ut labore et dolore magna aliqua.",
			// "2º Lote", 120.00f);
			// Ticket ingresso3 = new Ticket(
			// "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
			// tempor incididunt ut labore et dolore magna aliqua.",
			// "3º Lote", 150.00f);

			// List<Ticket> ticketList = new ArrayList<>();
			// ticketList.add(ingresso1);
			// ticketList.add(ingresso2);

			// /*
			// * Persistindo os dados
			// */

			// ticketRepository.save(ingresso1);
			// ticketRepository.save(ingresso2);
			// ticketRepository.save(ingresso3);

			eventRepository.save(event1);
			eventRepository.save(event2);

			subEventRepository.save(subEvent1);
			subEventRepository.save(subEvent2);
			subEventRepository.save(subEvent3);
			subEventRepository.save(subEvent4);
			subEventRepository.save(subEvent5);

			TicketType ticketType1 = new TicketType(new TicketTypeId("Meia Estudante", 1, event1.getId()),
					BigDecimal.valueOf(50.00), "Necessario comprovante de meia entrada");
			TicketType ticketType2 = new TicketType(new TicketTypeId("Inteira", 1, event1.getId()),
					BigDecimal.valueOf(100.00), "Identidade");
			TicketType ticketType3 = new TicketType(new TicketTypeId("Meia Estudante", 1, event2.getId()),
					BigDecimal.valueOf(70.00), "Necessario comprovante de meia entrada");
			// Salvando os TicketTypes
			ticketTypeRepository.save(ticketType1);
			ticketTypeRepository.save(ticketType2);
			ticketTypeRepository.save(ticketType3);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(EventhubApplication.class, args);
	}

}
