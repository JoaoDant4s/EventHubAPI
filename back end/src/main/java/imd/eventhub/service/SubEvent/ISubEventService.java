package imd.eventhub.service.SubEvent;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.SubEvent;

@Service
public interface ISubEventService {
    public Boolean isValid(SubEvent event) throws Exception;

    public SubEvent save(SubEvent event)
            throws NotFoundException, NullParameterException, InvalidParameterException, DateOutOfRangeException;

    public void deactivate(SubEvent event) throws Exception;

    public Optional<SubEvent> getByID(Integer id) throws NullParameterException;

    public List<SubEvent> getList();

    public List<SubEvent> getListByEventid(Integer id) throws NotFoundException, NullParameterException;
}
