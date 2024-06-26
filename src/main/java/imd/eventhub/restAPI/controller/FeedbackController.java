package imd.eventhub.restAPI.controller;

import imd.eventhub.exception.*;
import imd.eventhub.restAPI.dto.feedback.SaveFeedbackDTO;
import imd.eventhub.restAPI.dto.feedback.UpdateFeedbackDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.restAPI.infra.RestErrorMessage;
import imd.eventhub.restAPI.infra.RestSuccessMessage;
import imd.eventhub.service.Feedback.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {

    @Autowired
    IFeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<Object> getFeedbackList(){
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.getList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getFeedbackById(@PathVariable Integer id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(feedbackService.getById(id).get());
        } catch (NotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveFeedback(@RequestBody SaveFeedbackDTO feedbackDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.save(feedbackDTO));
        } catch(NotFoundException | RatingOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(400, HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> feedbackDelete(@PathVariable Integer id){
        try {
            feedbackService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new RestSuccessMessage(HttpStatus.OK, "Feedback apagado com sucesso!"));
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(404, HttpStatus.NOT_FOUND, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Object> feedbackUpdate(@RequestBody UpdateFeedbackDTO feedback){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(feedbackService.update(feedback));
        } catch(NotFoundException | RatingOutOfRangeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(400, HttpStatus.BAD_REQUEST, exception.getMessage()));
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }
}
