package imd.eventhub.service.Feedback;
import imd.eventhub.model.Feedback;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface IFeedbackService {

    public void save(Feedback feedback);
    public void delete(Feedback feedback);
    public Optional<Feedback> getById(Integer id);
    public List<Feedback> getList();
}
