package org.sid.cinema.web.administration;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.entities.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CinemasCrudController {
    @Autowired
    private CinemaRepository cinemaRepository;

    @GetMapping("/cinemasList")
    public String cinemasList(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "idKeyword", defaultValue = "") String idKeyword,
            @RequestParam(name = "nameKeyword", defaultValue = "") String nameKeyword,
            @RequestParam(name = "cityKeyword", defaultValue = "") String cityKeyword

    ) {
        Page<Cinema> cinemas;
        if(!idKeyword.isBlank())
        {
            try {
                long id = Long.parseLong(idKeyword);
                cinemas = cinemaRepository.findById(id, PageRequest.of(page, size));
            } catch (NumberFormatException e) {
                cinemas = cinemaRepository.findAll(PageRequest.of(page, size));
                System.out.println("NumberFormatException: " + e.getMessage());
            }

        }
        else if(!nameKeyword.isBlank())
        {
            cinemas = cinemaRepository.findByNameContains(nameKeyword, PageRequest.of(page, size));

        }
        else if(!cityKeyword.isBlank()){
           cinemas = cinemaRepository.findByVilleNameContains(cityKeyword, PageRequest.of(page, size));
        }
        else{
            cinemas = cinemaRepository.findAll(PageRequest.of(page, size));
        }
        model.addAttribute("cinemas", cinemas.getContent());
        model.addAttribute("pages", cinemas.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("idKeyword", idKeyword);
        model.addAttribute("nameKeyword", nameKeyword);
        model.addAttribute("cityKeyword", cityKeyword);
        model.addAttribute("size", size);

        return "cinemasList";
    }

    @GetMapping("/deleteCinema")
    public String deleteCinema(
            Long id, @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        cinemaRepository.deleteById(id);
        return "redirect:/cinemasList?page=" + page + "&keyword=" + keyword + "&size=" + size;
    }
}
