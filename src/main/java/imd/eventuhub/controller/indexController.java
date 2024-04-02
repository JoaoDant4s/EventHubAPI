package imd.eventuhub.controller;

import imd.eventuhub.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping("/")
        public String index(){
            return "index";
        }
}
