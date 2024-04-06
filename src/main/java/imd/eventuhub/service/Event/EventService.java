package imd.eventuhub.service.Event;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventuhub.model.Event;
import imd.eventuhub.repository.IEventRepository;

@Component
public class EventService implements IEventService{

    @Autowired
    IEventRepository eventRepository;

    @Override
    public void save(Event event) throws Exception{
        if(isValid(event)){
            eventRepository.save(event);
        }
    }

    @Override
    public void delete(Event event) throws Exception {
        if(isValid(event)){
            eventRepository.delete(event);
        }
    }

    @Override
    public Optional<Event> getByID(Integer id) throws Exception{
        if(id == null) throw new Exception("O id passado é nulo");
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> getList() throws Exception{
        return eventRepository.findAll();
    }
    @Override 
    public Boolean isValid(Event event) throws Exception{
        if(event == null) throw new Exception("Evento nulo");
        if(event.getDescription().isEmpty()) throw new Exception("Descrição do evento vazia");
        if(event.getLotacaoMaxima() <= 0) throw new Exception("Lotação máxima não pode ser menor que zero");
        if(event.getName().isEmpty()) throw new Exception("Nome do evento vazio");
        if(event.getDescription().isEmpty()) throw new Exception("Descrição do evento vazia");
        if(event.getAddress().isEmpty()) throw new Exception("Endereço do evento vazio");
        
        Duration duration = Duration.between(event.getInitialDate(), event.getFinalDate());
        if(duration.isNegative()) throw new Exception("Data final vem antes do que a data inicial");
        if(event.getType() == null) throw new Exception("Tipo do evento nulo");
        return true;
    }
}