package org.sid.cinema.web.administration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class specialPagesController {


    @GetMapping("/notAuthorized")
    public String roomsList() {
        return "specialPages/notAuthorized";
    }


}
