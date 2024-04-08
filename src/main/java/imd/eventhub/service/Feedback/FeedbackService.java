package imd.eventhub.service.Feedback;
import imd.eventhub.model.Feedback;
import imd.eventhub.repository.IFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class FeedbackService implements IFeedbackService {

    @Autowired
    IFeedbackRepository feedbackRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public void save(Feedback attraction){
        attraction.setDateTime(LocalDateTime.now());
        feedbackRepository.save(attraction);
    }

    @Override
    public void delete(Feedback participant){
        feedbackRepository.delete(participant);
    }

    @Override
    public Optional<Feedback> getById(Integer id){
        return feedbackRepository.findById(id);
    }
    
    @Override
    public List<Feedback> getList(){
        return feedbackRepository.findAll();
    }

}
