package imd.eventhub.service.Event;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.model.Event;
import imd.eventhub.repository.IEventRepository;
import imd.eventhub.service.SubEvent.ISubEventService;

@Component
public class EventService implements IEventService{

    @Autowired
    IEventRepository eventRepository;

    @Autowired
    ISubEventService subEventService;

    @Override
    public void save(Event event) throws Exception{
        if(isValid(event)){
            eventRepository.save(event);
        }
    }

    @Override
    public void deactivate(Event event) throws Exception {
        if(isValid(event)){
            if(!event.getSubEvents().isEmpty()){
                event.getSubEvents().forEach(subEvent -> {
                    try {
                        subEventService.deactivate(subEvent);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());;
                    }
                });
            }
            event.setActive(false);
            event.setSubEvents(null);
            eventRepository.save(event);
        }
    }

    @Override
    public Optional<Event> getByID(Integer id) throws Exception{
        if(id == null) throw new Exception("O id passado é nulo");
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> getList(){
        return eventRepository.findAllEventsByActive(true);
    }
    @Override 
    public Boolean isValid(Event event) throws Exception{
        if(event == null) throw new Exception("Evento nulo");
        if(event.getDescription().isEmpty()) throw new Exception("Descrição do evento vazia");
        if(event.getMaximumCapacity() <= 0) throw new Exception("Lotação máxima não pode ser menor que zero");
        if(event.getName().isEmpty()) throw new Exception("Nome do evento vazio");
        if(event.getDescription().isEmpty()) throw new Exception("Descrição do evento vazia");
        if(event.getAddress().isEmpty()) throw new Exception("Endereço do evento vazio");
        
        Duration duration = Duration.between(event.getInitialDate(), event.getFinalDate());
        if(duration.isNegative()) throw new Exception("Data final vem antes do que a data inicial");
        if(event.getType() == null) throw new Exception("Tipo do evento nulo");
        return true;
    }
}