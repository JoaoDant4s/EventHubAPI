package imd.eventuhub.service.SubEvent;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import imd.eventuhub.model.SubEvent;

@Service
public interface ISubEventService {
    public Boolean isValid(SubEvent event) throws Exception;
    public void save(SubEvent event) throws Exception;
    public void delete(SubEvent event) throws Exception;
    public Optional<SubEvent> getByID(Integer id) throws Exception;
    public List<SubEvent> getList() throws Exception;
}