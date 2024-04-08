package imd.eventhub.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import imd.eventhub.model.CreditCard;
import imd.eventhub.service.CreditCard.ICreditCardService;


@Controller
@RequestMapping("creditCard")
public class CreditCardController {

    @Autowired
    ICreditCardService creditCardService;

    @RequestMapping("/creditCardForm")
    public String creditCardForm(Model model) {
        model.addAttribute("creditCard", new CreditCard());
        model.addAttribute("method", "save");
        return "creditCard/creditCardForm";
    }

    @RequestMapping("/creditCardDetails")
    public String creditCardDetails(Model model) {
        try {
            Optional<CreditCard> creditCard = creditCardService.getByID(1);
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
    
}
