package org.sid.cinema.web.administration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class specialPagesController {


    @GetMapping("/notAuthorized")
    public String notAuthorized() {
        return "specialPages/notAuthorized";
    }

    @GetMapping("/login")
    public String login() {
        return "specialPages/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login";
    }


}
