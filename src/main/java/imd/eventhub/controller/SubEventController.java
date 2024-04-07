package imd.eventhub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
