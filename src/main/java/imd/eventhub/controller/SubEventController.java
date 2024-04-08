package imd.eventhub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import imd.eventhub.model.Event;
import imd.eventhub.model.SubEvent;
import imd.eventhub.service.Event.EventService;
import imd.eventhub.service.SubEvent.SubEventService;


@Controller
@RequestMapping("subEvent")
public class SubEventController {

    @Autowired
    SubEventService subEventService;

    @Autowired
    EventService eventService;

    @RequestMapping("subEventList/{id}")
    public String subEventList(@PathVariable("id") Integer id, Model model){
        try {
            Optional<Event> event = eventService.getByID(id);
            if(!event.isPresent()) throw new Exception("Não existe nenhum evento com esse id");
            List<SubEvent> subEvents = subEventService.getListByEventid(event.get().getId());
            model.addAttribute("event", event.get());
            model.addAttribute("subEvents", subEvents);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "subEvent/subEventList";
    }
    @RequestMapping("subEventForm/{id}")
    public String subEventForm(@PathVariable("id") Integer id, Model model){
        try {
            Optional<Event> event = eventService.getByID(id);
            if(!event.isPresent()) throw new Exception("Não existe nenhum evento com esse id");
            model.addAttribute("event", event.get());
            model.addAttribute("method", "save");
            model.addAttribute("subEvent", new SubEvent(event.get()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "subEvent/subEventForm";
    }

    @RequestMapping(value="/addSubEvent", method=RequestMethod.POST)
    public RedirectView addEvent(@ModelAttribute("subEvent") SubEvent subEvent, Model model) {
        try{
            subEventService.save(subEvent);
            Optional<Event> event = eventService.getByID(1);
            model.addAttribute("event", event.get());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new RedirectView("/event/eventList");
    }

    @RequestMapping("/updateSubEvent/{id}")
    public String updateSubEvent(@PathVariable("id") Integer id, Model model) {
        try{
            Optional<SubEvent> subEvent = subEventService.getByID(id);
            if(!subEvent.isPresent()) throw new Exception("Não existe nenhum sub-evento com esse id");
            model.addAttribute("subEvent", subEvent.get());
            model.addAttribute("method", "update");
            return "subEvent/subEventForm";
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "event/eventList";
        }
    }
    

    @RequestMapping("/updateSubEvent")
    public RedirectView updateSubEvent(@ModelAttribute("subEvent") SubEvent subEvent) {
        try {

            Optional<SubEvent> subEventToEdit = subEventService.getByID(subEvent.getId());
            if(!subEventToEdit.isPresent()) throw new Exception("Não existe nenhum sub-evento com esse id");
            subEventService.save(subEvent);
            return new RedirectView("/event/eventInfo/" + subEventToEdit.get().getId());
        } catch (Exception e) {
           System.out.println(e.getMessage());
           return new RedirectView("/event/eventList");
        }
    }

    @RequestMapping("/deleteSubEvent/{id}")
    public RedirectView deleteSubEvent(@PathVariable("id") Integer id) {
        try {
            Optional<SubEvent> subEvent = subEventService.getByID(id);
            if(!subEvent.isPresent()) throw new Exception("Não existe nenhum sub-evento com esse id");
            subEventService.deactivate(subEvent.get());
            return new RedirectView("/event/eventInfo/" + subEvent.get().getEvent().getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new RedirectView("/event/eventList");
        }
    }
    
    
}
