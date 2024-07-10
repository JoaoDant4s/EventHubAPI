package imd.eventhub.service.Feedback;

import java.util.List;
import java.util.Optional;

import imd.eventhub.restAPI.dto.feedback.FeedbackDTO;
import imd.eventhub.restAPI.dto.feedback.SaveFeedbackDTO;
import imd.eventhub.restAPI.dto.feedback.UpdateFeedbackDTO;
import org.springframework.stereotype.Service;

@Service
public interface IFeedbackService {

    public List<FeedbackDTO> getList();
    public Optional<FeedbackDTO> getById(Integer id);
    public FeedbackDTO save(SaveFeedbackDTO feedback);
    public FeedbackDTO update(UpdateFeedbackDTO feedback);
    public void delete(Integer id);
}
