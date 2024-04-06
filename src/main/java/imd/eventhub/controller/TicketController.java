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
import imd.eventhub.model.Ticket;
import imd.eventhub.service.Ticket.ITicketService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    ITicketService ticketService;

    @RequestMapping("/ticketList")
    public String ticketList(Model model){
        List<Ticket> ticketList = ticketService.getList();
        model.addAttribute("ticketList", ticketList);
        return "ticket/ticketList";
    }

    @RequestMapping("/ticketForm")
    public String ticketForm(Model model){

        model.addAttribute("ticket", new Ticket());
        model.addAttribute("method", "save");
        return "ticket/ticketForm";
    }

    @RequestMapping(value="/addTicket", method= RequestMethod.POST)
    public RedirectView addTicket(@ModelAttribute("ticket") Ticket ticket){
            ticketService.save(ticket);
            return new RedirectView("/ticket/ticketList");
    }

    @RequestMapping("/updateTicket/{id}")
    public String updateTicket(@PathVariable("id") Integer id, Model model){
        Optional<Ticket> ticket = ticketService.getById(id);
        model.addAttribute("ticket", ticket.get());
        model.addAttribute("method", "update");
        return "ticket/ticketForm";
    }

    @RequestMapping(value="/updateTicket", method=RequestMethod.POST)
    public RedirectView updateTicket(@ModelAttribute("ticket") Ticket ticket, Model model){
        ticketService.save(ticket);
        return new RedirectView("/ticket/ticketList");
    }

    @RequestMapping("/deleteTicket/{id}")
    public RedirectView deleteTicket(@PathVariable("id") Integer id){
        Optional<Ticket> ticket = ticketService.getById(id);
        ticketService.delete(ticket.get());
        return new RedirectView("/ticket/ticketList");
    }
}
