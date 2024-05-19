package imd.eventhub.service.Event;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Event;

@Service
public interface IEventService {
    public Boolean isValid(Event event) throws Exception;
    public Event save(Event event) throws NullParameterException, InvalidParameterException, DateOutOfRangeException;
    public void deactivate(Event event) throws Exception;
    public Optional<Event> getByID(Integer id) throws NullParameterException;
    public List<Event> getList();
}
