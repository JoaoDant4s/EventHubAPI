package imd.eventhub.service.Event;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NullParameterException;
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
    public Event save(Event event) throws NullParameterException, InvalidParameterException, DateOutOfRangeException{
        if(isValid(event)){
            return eventRepository.save(event);
        }
        return null;
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
    public Optional<Event> getByID(Integer id) throws NullParameterException{
        if(id == null) throw new NullParameterException("O id passado é nulo");
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> getList(){
        return eventRepository.findAllEventsByActive(true);
    }
    @Override 
    public Boolean isValid(Event event) throws NullParameterException, InvalidParameterException, DateOutOfRangeException{
        if(event == null) throw new NullParameterException("Evento nulo");
        if(event.getDescription().isEmpty()) throw new NullParameterException("Descrição do evento vazia");
        if(event.getMaximumCapacity() <= 0) throw new InvalidParameterException("Lotação máxima não pode ser menor que zero");
        if(event.getName().isEmpty()) throw new NullParameterException("Nome do evento vazio");
        if(event.getAddress().isEmpty()) throw new NullParameterException("Endereço do evento vazio");
        
        Duration duration = Duration.between(event.getInitialDate(), event.getFinalDate());
        if(duration.isNegative()) throw new DateOutOfRangeException("Data final vem antes do que a data inicial");
        if(event.getType() == null) throw new NullParameterException("Tipo do evento nulo");
        return true;
    }
}