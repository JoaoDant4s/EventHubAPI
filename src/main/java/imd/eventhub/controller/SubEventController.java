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
            model.addAttribute("subEvent", subEvent);
            model.addAttribute("event", subEvent.getEvent());
            return new RedirectView("/event/eventList");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new RedirectView("/event/eventList");
        }
    }
}
