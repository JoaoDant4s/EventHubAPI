package imd.eventhub.service.SubEvent;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.model.Event;
import imd.eventhub.model.SubEvent;
import imd.eventhub.repository.ISubEventRepository;
import imd.eventhub.service.Event.IEventService;

@Component
public class SubEventService implements ISubEventService{
    @Autowired
    ISubEventRepository subEventRepository;

    @Autowired
    IEventService eventService;

    @Override
    public void save(SubEvent subEvent) throws Exception {
        if(isValid(subEvent)){
            Optional<Event> event = eventService.getByID(subEvent.getEvent().getId());
            if(!event.isPresent()) throw new Exception("O evento não existe");
            subEventRepository.save(subEvent);
        }
    }

    @Override
    public void delete (SubEvent subEvent) throws Exception {
        if(isValid(subEvent)){
            subEventRepository.delete(subEvent);
        }
    }

    @Override
    public Optional<SubEvent> getByID(Integer id) throws Exception{
        if(id == null) throw new Exception("O id passado é nulo");
        return subEventRepository.findById(id);
    }

    @Override
    public List<SubEvent> getList() throws Exception {
        return subEventRepository.findAll();
    }

    @Override
    public List<SubEvent> getListByEventid(Integer id) throws Exception {
        Optional<Event> event = eventService.getByID(id);
        if(!event.isPresent()) throw new Exception("Não existe nenhum evento com esse id");
        return subEventRepository.getAllSubEventsByEvent(event.get());
    }

    @Override
    public Boolean isValid(SubEvent subEvent) throws Exception{
        if(subEvent == null) throw new Exception("SubEvento é nulo");
        if(subEvent.getLocation().isEmpty()) throw new Exception("Local do SubEvento está vazio");
        if(subEvent.getDescription().isEmpty()) throw new Exception("Descrição do SubEvento é vazia");
        if(subEvent.getHours() == null) throw new Exception("Dia e hora são nulos");
        if(subEvent.getType() == null) throw new Exception("Tipo do SubEvento é nulo");
        return true;
    }
}
