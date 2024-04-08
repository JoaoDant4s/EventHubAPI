package imd.eventhub.controller;

import imd.eventhub.model.*;
import imd.eventhub.service.Event.IEventService;
import imd.eventhub.service.SubEvent.ISubEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import imd.eventhub.model.Ticket;
import imd.eventhub.service.Ticket.ITicketService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    ITicketService ticketService;

    @Autowired
    IEventService eventService;

    @Autowired
    ISubEventService subEventService;

    @RequestMapping("/ticketList")
    public String ticketList(Model model){
        List<Ticket> ticketList = ticketService.getList();
        model.addAttribute("ticketList", ticketList);
        return "ticket/ticketList";
    }

    @RequestMapping("/ticketForm")
    public String ticketForm(Model model){

        model.addAttribute("ticket", new Ticket());
        model.addAttribute("eventList", eventService.getList());
        model.addAttribute("method", "save");
        return "ticket/ticketForm";
    }

    @RequestMapping(value="/addTicket", method= RequestMethod.POST)
    public RedirectView addTicket(@ModelAttribute("ticket") Ticket ticket, @RequestParam("subEventIdList") List<Integer> subEventIdList){
        List<SubEvent> subEventsList = new ArrayList<>();
        subEventIdList.forEach(id->{
            try {
                subEventsList.add(subEventService.getByID(id).get());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        ticket.setSubEvent(subEventsList);
        ticketService.save(ticket);
        return new RedirectView("/ticket/ticketList");
    }

    @RequestMapping("/updateTicket/{id}")
    public String updateTicket(@PathVariable("id") Integer id, Model model){
        Optional<Ticket> ticket = ticketService.getById(id);
        model.addAttribute("ticket", ticket.get());
        model.addAttribute("eventList", eventService.getList());
        model.addAttribute("method", "update");
        return "ticket/ticketForm";
    }

    @RequestMapping(value="/updateTicket", method=RequestMethod.POST)
    public RedirectView updateTicket(@ModelAttribute("ticket") Ticket ticket, Model model, @RequestParam("subEventIdList") List<Integer> subEventIdList){
        List<SubEvent> subEventsList = new ArrayList<>();
        subEventIdList.forEach(id->{
            try {
                subEventsList.add(subEventService.getByID(id).get());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        ticket.setSubEvent(subEventsList);
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
