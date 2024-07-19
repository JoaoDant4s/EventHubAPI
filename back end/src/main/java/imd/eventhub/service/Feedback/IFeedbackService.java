package imd.eventhub.service.Feedback;

import java.util.List;
import java.util.Optional;

import imd.eventhub.exception.RatingOutOfRangeException;
import imd.eventhub.restAPI.dto.feedback.FeedbackDTO;
import imd.eventhub.restAPI.dto.feedback.SaveFeedbackDTO;
import imd.eventhub.restAPI.dto.feedback.UpdateFeedbackDTO;
import org.springframework.stereotype.Service;

@Service
public interface IFeedbackService {

    public List<FeedbackDTO> getList();
    public Optional<FeedbackDTO> getById(Integer id);
    public FeedbackDTO save(SaveFeedbackDTO feedback) throws RatingOutOfRangeException;
    public FeedbackDTO update(UpdateFeedbackDTO feedback) throws RatingOutOfRangeException;
    public void delete(Integer id);
}
