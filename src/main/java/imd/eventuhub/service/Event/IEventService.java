package imd.eventuhub.service.Event;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import imd.eventuhub.model.Event;

@Service
public interface IEventService {
    public Boolean isValid(Event event) throws Exception;
    public void save(Event event) throws Exception;
    public void delete(Event event) throws Exception;
    public Optional<Event> getByID(Integer id) throws Exception;
    public List<Event> getList() throws Exception;
}
