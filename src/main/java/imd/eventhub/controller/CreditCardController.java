package imd.eventhub.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import imd.eventhub.model.CreditCard;
import imd.eventhub.model.Participant;
import imd.eventhub.service.CreditCard.ICreditCardService;
import imd.eventhub.service.Person.Interfaces.IParticipantService;


@Controller
@RequestMapping("creditCard")
public class CreditCardController {

    @Autowired
    ICreditCardService creditCardService;

    @Autowired
    IParticipantService participantService;

    @RequestMapping("/creditCardForm")
    public String creditCardForm(Model model) {
        model.addAttribute("creditCard", new CreditCard());
        model.addAttribute("method", "save");
        return "creditCard/creditCardForm";
    }

    @RequestMapping("/creditCardDetails")
    public String creditCardDetails(Model model) {
        try {
            Optional<Participant> participant = participantService.getById(2);
            Optional<CreditCard> creditCard = creditCardService.getByParticipant(participant.get());
            model.addAttribute("creditCard", creditCard.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "/creditCard/creditCardDetails";
    }


    @RequestMapping("/createCreditCard")
    public RedirectView createCreditCard(@ModelAttribute("creditCard") CreditCard creditCard) {
        try {
            creditCardService.save(creditCard);
            return new RedirectView("/creditCard/creditCardDetails");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new RedirectView("/creditCard/creditCardForm");
        }
    }

    @RequestMapping("/deleteCreditCard/{id}")
    public RedirectView createCreditCard(@PathVariable("id") Integer id) {
        try {
            Optional<CreditCard> creditCard = creditCardService.getByID(id);
            if(!creditCard.isPresent()) throw new Exception("Não existe nenhum cartão de crédito com esse id");
            creditCardService.delete(creditCard.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/creditCard/creditCardDetails");
    }
    
}
