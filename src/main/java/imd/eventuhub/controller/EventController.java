package imd.eventuhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
public class EventController {
    

    @RequestMapping("/eventList")
    public String eventList(Model model){
        
        return "event/eventList";
    }
}
