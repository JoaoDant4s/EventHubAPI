package imd.eventhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import imd.eventhub.model.Attraction;
import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;
import imd.eventhub.service.Person.AttractionService;
import imd.eventhub.service.Person.ParticipantService;
import imd.eventhub.service.Person.PersonService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;
    @Autowired
    ParticipantService participantService;
    @Autowired
    AttractionService attractionService;

    @RequestMapping("/personList")
    public String personList(Model model){
        List<Participant> participantList = participantService.getList();
        List<Attraction> attractionList = attractionService.getList();
        model.addAttribute("participantCount", participantList.size());
        model.addAttribute("attractionCount", attractionList.size());
        model.addAttribute("personCount", participantList.size()+attractionList.size());
        model.addAttribute("participantList", participantList);
        model.addAttribute("attractionList", attractionList);
        return "person/personList";
    }

    @RequestMapping("/participantForm")
    public String participantForm(Model model){

        model.addAttribute("participant", new Participant());
        model.addAttribute("method", "save");
        return "person/participantForm";
    }

    @RequestMapping(value="/addParticipant", method=RequestMethod.POST)
    public RedirectView addParticipant(@ModelAttribute("participant") Participant participant){
        Optional<Person> participantWithSameCpf = personService.getByCpf(participant.getCpf());

        if(participantWithSameCpf.isPresent()){
            return new RedirectView("/person/participantForm");
        } else {
            personService.save(participant);
            return new RedirectView("/person/personList");
        }
    }

    @RequestMapping("/attractionForm")
    public String attractionForm(Model model){

        model.addAttribute("attraction", new Attraction());
        model.addAttribute("method", "save");
        return "person/attractionForm";
    }

    @RequestMapping(value="/addAttraction", method=RequestMethod.POST)
    public RedirectView addAttraction(@ModelAttribute("participant") Attraction attraction){
        Optional<Person> participantWithSameCpf = personService.getByCpf(attraction.getCpf());

        if(participantWithSameCpf.isPresent()){
            return new RedirectView("/person/attractionForm");
        } else {
            personService.save(attraction);
            return new RedirectView("/person/personList");
        }
    }

    @RequestMapping("/updateParticipant/{id}")
    public String updateParticipant(@PathVariable("id") Integer id, Model model){
        Optional<Participant> participant = participantService.getById(id);
        model.addAttribute("participant", participant.get());
        model.addAttribute("method", "update");
        return "person/participantForm";
    }

    @RequestMapping(value="/updateParticipant", method=RequestMethod.POST)
    public RedirectView updateParticipant(@ModelAttribute("participant") Participant participant, Model model){
        Optional<Person> participantWithSameCpf = personService.getByCpf(participant.getCpf());
        Optional<Participant> getParticipant = participantService.getById(participant.getId());
        participant.setTicketList(getParticipant.get().getTicketList());

        if(participantWithSameCpf.isPresent() && participant.getId() == participantWithSameCpf.get().getId()) {
            personService.save(participant);
            return new RedirectView("/person/personList");
        }else if(participantWithSameCpf.isEmpty()){
            personService.save(participant);
            return new RedirectView("/person/personList");
        }else {
            return new RedirectView("/person/updateParticipant/"+participant.getId());
        }
    }

    @RequestMapping("/updateAttraction/{id}")
    public String updateAttraction(@PathVariable("id") Integer id, Model model){
        Optional<Attraction> attraction = attractionService.getById(id);
        model.addAttribute("attraction", attraction.get());
        model.addAttribute("method", "update");
        return "person/attractionForm";
    }

    @RequestMapping(value="/updateAttraction", method=RequestMethod.POST)
    public RedirectView updateAttraction(@ModelAttribute("attraction") Attraction attraction, Model model){
        Optional<Person> attractionWithSameCpf = personService.getByCpf(attraction.getCpf());


        if(attractionWithSameCpf.isPresent() && attraction.getId() == attractionWithSameCpf.get().getId()) {
            personService.save(attraction);
            return new RedirectView("/person/personList");
        }else if(attractionWithSameCpf.isEmpty()){
            personService.save(attraction);
            return new RedirectView("/person/personList");
        }else {
            return new RedirectView("/person/updateAttraction/"+attraction.getId());
        }
    }

    @RequestMapping("/deleteParticipant/{id}")
    public RedirectView deleteParticipant(@PathVariable("id") Integer id){
        Optional<Participant> participant = participantService.getById(id);
        participantService.delete(participant.get());
        return new RedirectView("/person/personList");
    }

    @RequestMapping("/deleteAttraction/{id}")
    public RedirectView deleteAttraction(@PathVariable("id") Integer id){
        Optional<Attraction> attraction = attractionService.getById(id);
        attractionService.delete(attraction.get());
        return new RedirectView("/person/personList");
    }

    @RequestMapping("/participantProfile/{id}")
    public String participantProfile(@PathVariable("id") Integer id, Model model){
        Optional<Participant> participant = participantService.getById(id);
        model.addAttribute("participant", participant.get());
        model.addAttribute("ticketList", participant.get().getTicketList());
        return "person/participantProfile";
    }

}
