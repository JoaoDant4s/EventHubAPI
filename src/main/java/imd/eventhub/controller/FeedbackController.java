package imd.eventhub.controller;

import imd.eventhub.service.Event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.Feedback;
import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;
import imd.eventhub.service.Feedback.FeedbackService;
import imd.eventhub.service.Person.AttractionService;
import imd.eventhub.service.Person.ParticipantService;
import imd.eventhub.service.Person.PersonService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    PersonService personService;
    @Autowired
    ParticipantService participantService;
    @Autowired
    AttractionService attractionService;
    @Autowired
    EventService eventService;
    @Autowired
    FeedbackService feedbackService;

    @RequestMapping("/feedbackList")
    public String feedbackList(Model model){
        model.addAttribute("feedbackList", feedbackService.getList());
        return "feedback/feedbackList";
    }

    @RequestMapping("/feedbackForm")
    public String feedbackForm(Model model){
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("participantList", participantService.getList());
        model.addAttribute("eventList", eventService.getList());
        return "feedback/feedbackForm";
    }

    @RequestMapping("/feedbackForm/{participantId}/{ticketId}")
    public String feedbackForm(@PathVariable("participantId") Integer participantId, @PathVariable("ticketId") Integer ticketId, Model model){
        Optional<Participant> participant = participantService.getById(participantId);
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("participant", participant.get());
        return "feedback/feedbackForm";
    }

    @RequestMapping(value="/addFeedback", method=RequestMethod.POST)
    public RedirectView addFeedback(@ModelAttribute("feedback") Feedback feedback, @RequestParam("participant") Integer participantId, @RequestParam("event") Integer eventId) throws Exception {
        feedback.setParticipant(participantService.getById(participantId).get());
        feedback.setEvent(eventService.getByID(eventId).get());
        feedbackService.save(feedback);
        return new RedirectView("/feedback/feedbackList");
    }

    @RequestMapping("/deleteFeedback/{id}")
    public RedirectView deleteFeedback(@PathVariable("id") Integer id){
        Optional<Feedback> feedback = feedbackService.getById(id);
        feedbackService.delete(feedback.get());
        return new RedirectView("/feedback/feedbackList");
    }

}
