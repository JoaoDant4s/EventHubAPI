package imd.eventhub.service.Feedback;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.RatingOutOfRangeException;
import imd.eventhub.model.Event;
import imd.eventhub.model.Feedback;
import imd.eventhub.model.User;
import imd.eventhub.repository.IEventRepository;
import imd.eventhub.repository.IFeedbackRepository;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.restAPI.dto.feedback.FeedbackDTO;
import imd.eventhub.restAPI.dto.feedback.SaveFeedbackDTO;
import imd.eventhub.restAPI.dto.feedback.UpdateFeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class FeedbackService implements IFeedbackService {

    @Autowired
    IFeedbackRepository feedbackRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IEventRepository eventRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public List<FeedbackDTO> getList(){
        List<FeedbackDTO> feedbackDTOList = feedbackRepository.findAll().stream().map(feedback-> {
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            feedbackDTO.setId(feedback.getId());
            feedbackDTO.setComment(feedback.getComment());
            feedbackDTO.setRating(feedback.getRating());
            feedbackDTO.setDateTime(feedback.getDateTime());
            feedbackDTO.setUserId(feedback.getUser().getId());
            feedbackDTO.setEventId(feedback.getEvent().getId());
            return feedbackDTO;
        }).collect(Collectors.toList());

        return feedbackDTOList;
    }

    @Override
    public Optional<FeedbackDTO> getById(Integer id){
        Optional<FeedbackDTO> getFeedback = feedbackRepository.findById(id).stream().findAny().map(feedback -> {
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            feedbackDTO.setId(feedback.getId());
            feedbackDTO.setComment(feedback.getComment());
            feedbackDTO.setRating(feedback.getRating());
            feedbackDTO.setDateTime(feedback.getDateTime());
            feedbackDTO.setUserId(feedback.getUser().getId());
            feedbackDTO.setEventId(feedback.getEvent().getId());
            return feedbackDTO;
        });
        if(getFeedback.isEmpty()){
            throw new NotFoundException("Feedback não encontrado");
        }

        return getFeedback;
    }

    @Override
    public FeedbackDTO save(SaveFeedbackDTO feedbackDTO){

        Optional<User> user = userRepository.findById(feedbackDTO.getUserId());
        Optional<Event> event = eventRepository.findById(feedbackDTO.getEventId());

        if(feedbackDTO.getComment() == null){
            throw new NotFoundException("campo 'comment' não foi encontrado");
        }
        if(feedbackDTO.getRating() != null){
            if(feedbackDTO.getRating() < 1 || feedbackDTO.getRating() > 5){
                throw new RatingOutOfRangeException("A avaliação deve ser um número maior ou igual a 1 e menor ou igual a 5");
            }
        }
        if(user.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }
        if(event.isEmpty()){
            throw new NotFoundException("Evento não encontrado");
        }

        Feedback feedback = new Feedback();
        feedback.setEvent(event.get());
        feedback.setUser(user.get());
        feedback.setComment(feedbackDTO.getComment());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setDateTime(LocalDateTime.now());

        Feedback savedFeedback = feedbackRepository.save(feedback);

        FeedbackDTO feedbackDTO1 = new FeedbackDTO();
        feedbackDTO1.setId(savedFeedback.getId());
        feedbackDTO1.setComment(savedFeedback.getComment());
        feedbackDTO1.setRating(savedFeedback.getRating());
        feedbackDTO1.setUserId(user.get().getId());
        feedbackDTO1.setEventId(event.get().getId());
        feedbackDTO1.setDateTime(savedFeedback.getDateTime());

        return feedbackDTO1;
    }

    @Override
    public FeedbackDTO update(UpdateFeedbackDTO feedbackDTO) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackDTO.getId());

        if(feedback.isEmpty()){
            throw new NotFoundException("Feedback não encontrado");
        }

        if(feedbackDTO.getComment() == null){
            throw new NotFoundException("campo 'comment' não foi encontrado");
        }
        if(feedbackDTO.getRating() != null){
            if(feedbackDTO.getRating() < 1 || feedbackDTO.getRating() > 5){
                throw new RatingOutOfRangeException("A avaliação deve ser um número maior ou igual a 1 e menor ou igual a 5");
            }
        }

        feedback.get().setComment(feedbackDTO.getComment());
        feedback.get().setRating(feedbackDTO.getRating());

        Feedback savedFeedback = feedbackRepository.save(feedback.get());

        FeedbackDTO feedbackDTO1 = new FeedbackDTO();
        feedbackDTO1.setId(savedFeedback.getId());
        feedbackDTO1.setComment(savedFeedback.getComment());
        feedbackDTO1.setRating(savedFeedback.getRating());
        feedbackDTO1.setUserId(savedFeedback.getUser().getId());
        feedbackDTO1.setEventId(savedFeedback.getEvent().getId());
        feedbackDTO1.setDateTime(savedFeedback.getDateTime());

        return feedbackDTO1;
    }

    @Override
    public void delete(Integer id){
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if(feedback.isEmpty()){
            throw new NotFoundException("Feedback não encontrado");
        }
        feedbackRepository.delete(feedback.get());
    }

}
