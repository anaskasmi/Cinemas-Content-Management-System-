package org.sid.cinema.web.administration;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class CinemasCrudController {
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    SalleRepository salleRepository;
    @Autowired
    PlaceRepository placeRepository;
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
        if (!idKeyword.isBlank()) {
            try {
                long id = Long.parseLong(idKeyword);
                cinemas = cinemaRepository.findById(id, PageRequest.of(page, size));
            } catch (NumberFormatException e) {
                cinemas = cinemaRepository.findAll(PageRequest.of(page, size));
                System.out.println("NumberFormatException: " + e.getMessage());
            }
        } else if (!nameKeyword.isBlank()) {
            cinemas = cinemaRepository.findByNameContains(nameKeyword, PageRequest.of(page, size));

        } else if (!cityKeyword.isBlank()) {
            cinemas = cinemaRepository.findByVilleNameContains(cityKeyword, PageRequest.of(page, size));
        } else {
            cinemas = cinemaRepository.findAll(PageRequest.of(page, size));
        }
        model.addAttribute("cinemas", cinemas.getContent());
        model.addAttribute("pages", cinemas.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("idKeyword", idKeyword);
        model.addAttribute("nameKeyword", nameKeyword);
        model.addAttribute("cityKeyword", cityKeyword);
        model.addAttribute("size", size);

        return "cinemas/cinemasList";
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

    @GetMapping(path = "/newCinema")
    public String newCinema(Model model) {

        List<Ville> cities = villeRepository.findAll();
        model.addAttribute("cinemaFormPayload", new CinemaFormPayload());
        model.addAttribute("cities", cities);

        return "cinemas/newCinema";
    }

    @PostMapping(path = "/saveCinema")
    public String saveCinema(@Valid CinemaFormPayload cinemaFormPayload, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
        {
            return "cinemas/newCinema";
        }
        Cinema cinema = new Cinema();
        Ville ville;
        System.out.println("payload : "+ cinemaFormPayload.toString());
        ville = villeRepository.findById(cinemaFormPayload.getCityId()).get();
        cinema.setVille(ville);
        cinema.setName(cinemaFormPayload.getName());
        cinema.setLatitude(cinemaFormPayload.getLatitude());
        cinema.setLongitude(cinemaFormPayload.getLongitude());
        cinema.setAltitude(cinemaFormPayload.getAltitude());
        cinema.setNombreSalles(cinemaFormPayload.getNombreSalles());
        System.out.println("ville : "+ ville.getName());
        System.out.println("cinema : "+ cinema.toString());
        cinemaRepository.save(cinema);
        for (int i = 0; i < cinema.getNombreSalles(); i++) {
            Salle salle = new Salle();
            salle.setName("salle " + (i + 1));
            salle.setCinema(cinema);
            salle.setNombrePlace(0);
            salleRepository.save(salle);
        }
        return "redirect:/cinemasList?idKeyword=" + cinema.getId();
    }
}
