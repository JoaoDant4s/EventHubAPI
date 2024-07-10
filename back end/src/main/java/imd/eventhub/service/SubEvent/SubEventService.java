package imd.eventhub.service.SubEvent;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Event;
import imd.eventhub.model.SubEvent;
import imd.eventhub.repository.ISubEventRepository;
import imd.eventhub.service.Event.IEventService;

@Component
public class SubEventService implements ISubEventService {
    @Autowired
    ISubEventRepository subEventRepository;

    @Autowired
    IEventService eventService;

    @Override
    public SubEvent save(SubEvent subEvent) throws NotFoundException, NullParameterException, InvalidParameterException, DateOutOfRangeException {
        if (isValid(subEvent)) {
            Optional<Event> event = eventService.getByID(subEvent.getEvent().getId());
            if (!event.isPresent())
                throw new NotFoundException("O evento não existe");
            event.get().addSubEvents(subEvent);
            eventService.save(event.get());
            subEventRepository.save(subEvent);
            return subEvent;
        }
        return null;
    }

    @Override
    public void deactivate(SubEvent subEvent) throws Exception {
        if (isValid(subEvent)) {
            Optional<SubEvent> subEventToEdit = subEventRepository.findById(subEvent.getId());
            if (!subEventToEdit.isPresent())
                throw new Exception("O ID informado não é de nenhum SubEvento");
            subEvent.setActive(false);
            subEventRepository.save(subEvent);
        }
    }

    @Override
    public Optional<SubEvent> getByID(Integer id) throws NullParameterException {
        if (id == null)
            throw new NullParameterException("O id passado é nulo");
        return subEventRepository.findById(id);
    }

    @Override
    public List<SubEvent> getList() {
        return subEventRepository.findAll();
    }

    @Override
    public List<SubEvent> getListByEventid(Integer id) throws NotFoundException, NullParameterException {
        Optional<Event> event = eventService.getByID(id);
        if (!event.isPresent())
            throw new NotFoundException("Não existe nenhum evento com esse id");
        return subEventRepository.getAllSubEventsByEventAndActive(event.get(), true);
    }

    @Override
    public Boolean isValid(SubEvent subEvent) throws NullParameterException {
        if (subEvent == null)
            throw new NullParameterException("SubEvento é nulo");
        if (subEvent.getLocation().isEmpty())
            throw new NullParameterException("Local do SubEvento está vazio");
        if (subEvent.getDescription().isEmpty())
            throw new NullParameterException("Descrição do SubEvento é vazia");
        if (subEvent.getHours() == null)
            throw new NullParameterException("Dia e hora são nulos");
        if (subEvent.getType() == null)
            throw new NullParameterException("Tipo do SubEvento é nulo");
        return true;
    }
}
