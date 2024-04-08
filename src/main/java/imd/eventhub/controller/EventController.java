package imd.eventhub.controller;

import java.util.List;
import java.util.Optional;

import imd.eventhub.model.SubEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import imd.eventhub.model.Event;
import imd.eventhub.model.SubEvent;
import imd.eventhub.service.Event.EventService;
import imd.eventhub.service.SubEvent.SubEventService;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/event")
public class EventController {
    
    @Autowired
    EventService eventService;

    @Autowired
    SubEventService subEventService;

    @RequestMapping("/eventList")
    public String eventList(Model model){
        List<Event> eventList = eventService.getList();
        model.addAttribute("eventList", eventList);
        return "event/eventList";
    }

    @RequestMapping("/eventForm")
    public String eventForm(Model model){
        model.addAttribute("event", new Event());
        model.addAttribute("method", "save");
        return "event/eventForm";
    }

    @RequestMapping(value="/addEvent", method=RequestMethod.POST)
    public RedirectView addEvent(@ModelAttribute("event") Event event) {
        try{
            eventService.save(event);
            return new RedirectView("/event/eventList");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new RedirectView("/event/eventForm");
        }
    }

    @RequestMapping("/updateEvent/{id}")
    public String updateEvent(@PathVariable("id") Integer id, Model model) {
        try {
            Optional<Event> event = eventService.getByID(id);
            if(!event.isPresent()) throw new Exception("Não existe nenhum evento com esse id");
            model.addAttribute("event", event.get());
            model.addAttribute("method", "update");
            return "event/eventForm";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "event/eventList";
        }
    }

    @RequestMapping("/updateEvent")
    public RedirectView updateEvent(@ModelAttribute("event") Event event) {
        try {
            Optional<Event> eventToEdit = eventService.getByID(event.getId());
            if(!eventToEdit.isPresent()) throw new Exception("Não existe nenhum evento com esse id");
            eventService.save(event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/event/eventList");
    }
    

    @RequestMapping("/deleteEvent/{id}")
    public RedirectView deleteEvent(@PathVariable("id") Integer id) {
        try {
            Optional<Event> event = eventService.getByID(id);
            if(!event.isPresent()) throw new Exception("Não existe nenhum evento com esse id");
            eventService.deactivate(event.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/event/eventList");
    }
    
    @RequestMapping("/eventInfo/{id}")
    public String showEventDetails(@PathVariable("id") Integer id, Model model) {
        try {
            Optional<Event> event = eventService.getByID(id);
            if(!event.isPresent()) throw new Exception("Não existe nenhum evento com esse id");
            List<SubEvent> subEvents = subEventService.getListByEventid(id);
            event.get().setSubEvents(subEvents);
            model.addAttribute("event", event.get());
            return "/event/eventInfo";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/event/eventList";
        }
    }
}
