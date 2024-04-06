package imd.eventhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("subevent")
public class SubEventController {
    @RequestMapping("subEventList")
    public String subEventList(Model model){
        return "subevent/subeventList";
    }
}
