package imd.eventuhub.controller;

import imd.eventuhub.model.Participant;
import imd.eventuhub.model.Person;
import imd.eventuhub.service.PersonService;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping("/personList")
    public String personList(Model model){
        List<Participant> personList = personService.getParticipantList();
        model.addAttribute("personList", personList);
        return "person/personList";
    }
}
