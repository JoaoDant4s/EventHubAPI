package imd.eventhub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import imd.eventhub.model.Event;
import imd.eventhub.service.Event.EventService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/event")
public class EventController {
    
    @Autowired
    EventService eventService;

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
            eventService.delete(event.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/event/eventList");
    }
    
    
}
