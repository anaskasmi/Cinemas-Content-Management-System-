package org.sid.cinema.web.administration;


import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.Ville;
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

@Controller
public class CitiesCrudController {
    @Autowired
    private VilleRepository villeRepository;


    @GetMapping("/citiesList")
    public String citiesList(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "nameKeyword", defaultValue = "") String nameKeyword

    ) {
        Page<Ville> cities;
        if (!nameKeyword.isBlank()) {
            cities = villeRepository.findByNameContains(nameKeyword, PageRequest.of(page, size));
        } else {
            cities = villeRepository.findAll(PageRequest.of(page, size));
        }
        model.addAttribute("cities", cities.getContent());
        model.addAttribute("pages", cities.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("nameKeyword", nameKeyword);
        model.addAttribute("size", size);

        return "cities/citiesList";
    }

    @GetMapping("/deleteCity")
    public String deleteCity(
            Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "nameKeyword", defaultValue = "") String nameKeyword
    ) {
        villeRepository.deleteById(id);
        return "redirect:/citiesList?page=" + page
                + "&nameKeyword=" + nameKeyword;
    }

    @GetMapping(path = "/newCity")
    public String newCity(Model model) {
        Ville city = new Ville();
        model.addAttribute("city", city);
        return "cities/newCity";
    }

    @GetMapping(path = "/showCity")
    public String showCity(
            Model model,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "nameKeyword", defaultValue = "") String nameKeyword
    ) {

        Ville city = villeRepository.findById(id).get();
        model.addAttribute("city", city);
        model.addAttribute("currentPage", page);
        model.addAttribute("nameKeyword", nameKeyword);
        return "cities/showCity";
    }

    @GetMapping(path = "/editCity")
    public String editCity(
            Model model,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "nameKeyword", defaultValue = "") String nameKeyword
    ) {
        Ville city = villeRepository.findById(id).get();

        model.addAttribute("city", city);
        model.addAttribute("currentPage", page);
        model.addAttribute("nameKeyword", nameKeyword);
        System.out.println("city before update: "+city.toString());
        return "cities/editCity";


    }

    @PostMapping(path = "/updateCity")
    public String updateCity(@Valid Ville city, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cities/editCity?id=" + city.getId();
        }
        System.out.println("city after update: "+city.toString());

        Ville cityToSave = villeRepository.findById(city.getId()).get();
        cityToSave.setName(city.getName());
        cityToSave.setAltitude(city.getAltitude());
        cityToSave.setLatitude(city.getLatitude());
        cityToSave.setLongitude(city.getLongitude());
        villeRepository.save(cityToSave);
        return "redirect:/showCity?id=" + city.getId();
    }

    @PostMapping(path = "/saveCity")
    public String saveCity(@Valid Ville city, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cities/newCity";
        }
        villeRepository.save(city);
        return "redirect:/citiesList?idKeyword=" + city.getId();
    }
}
