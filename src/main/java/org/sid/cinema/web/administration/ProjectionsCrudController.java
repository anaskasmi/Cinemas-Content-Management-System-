//package org.sid.cinema.web.administration;
//
//import org.sid.cinema.dao.CinemaRepository;
//import org.sid.cinema.dao.PlaceRepository;
//import org.sid.cinema.dao.SalleRepository;
//import org.sid.cinema.dao.VilleRepository;
//import org.sid.cinema.entities.Cinema;
//import org.sid.cinema.entities.Salle;
//import org.sid.cinema.entities.Ville;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.validation.Valid;
//import java.util.List;
//
////@Controller
//public class ProjectionsCrudController {
//    @Autowired
//    VilleRepository villeRepository;
//    @Autowired
//    private CinemaRepository cinemaRepository;
//    @Autowired
//    SalleRepository salleRepository;
//    @Autowired
//    PlaceRepository placeRepository;
//
//    @GetMapping("/projectionsList")
//    public String projectionsList(
//            Model model,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "15") int size,
//            @RequestParam(name = "cityNameKeyword", defaultValue = "") String cityNameKeyword,
//            @RequestParam(name = "cinemaNameKeyword", defaultValue = "") String cinemaNameKeyword,
//            @RequestParam(name = "roomNameKeyword", defaultValue = "") String roomNameKeyword
//    ) {
//        Page<Salle> rooms;
//        if (!cityNameKeyword.isBlank() && !cinemaNameKeyword.isBlank() && !roomNameKeyword.isBlank()) {
//            rooms = salleRepository.findByCinema_Ville_NameIgnoreCaseContainingAndCinema_NameAndNameIgnoreCaseContaining(
//                    cityNameKeyword, cinemaNameKeyword, roomNameKeyword, PageRequest.of(page, size));
//        } else if (!cityNameKeyword.isBlank() && !cinemaNameKeyword.isBlank()) {
//            rooms = salleRepository.findByCinema_Ville_NameIgnoreCaseContainingAndCinema_NameIgnoreCaseContaining(
//                    cityNameKeyword, cinemaNameKeyword, PageRequest.of(page, size));
//        } else {
//            rooms = salleRepository.findAll(PageRequest.of(page, size));
//        }
//        model.addAttribute("rooms", rooms.getContent());
//        model.addAttribute("cityNameKeyword", cityNameKeyword);
//        model.addAttribute("cinemaNameKeyword", cinemaNameKeyword);
//        model.addAttribute("roomNameKeyword", roomNameKeyword);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("pages", rooms.getTotalPages());
//
//        return "rooms/roomsList";
//    }
//
//    @GetMapping("/deleteProjection")
//    public String deleteProjection(
//            Long id,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "15") int size,
//            @RequestParam(name = "cityNameKeyword", defaultValue = "") String cityNameKeyword,
//            @RequestParam(name = "cinemaNameKeyword", defaultValue = "") String cinemaNameKeyword,
//            @RequestParam(name = "roomNameKeyword", defaultValue = "") String roomNameKeyword
//    ) {
//        salleRepository.deleteById(id);
//        return "redirect:/roomsList?page=" + page
//                + "&cityNameKeyword=" + cityNameKeyword
//                + "&cinemaNameKeyword=" + cinemaNameKeyword +
//                "&roomNameKeyword=" + roomNameKeyword;
//    }
//
//    @GetMapping(path = "/newRoom/selectCity")
//    public String newRoomSelectCity(Model model) {
//        List<Ville> cities = villeRepository.findAll();
//        model.addAttribute("cities", cities);
//        return "rooms/newRoom/selectCity";
//    }
//
//    @GetMapping(path = "/newRoom/selectCity/selectCinema")
//    public String newRoomSelectCinema(
//            Model model,
//            @RequestParam(name = "cityIdKeyword", defaultValue = "") Long cityIdKeyword
//    ) {
//
//        List<Cinema> cinemas = cinemaRepository.findByVille_Id(cityIdKeyword);
//        model.addAttribute("cinemas", cinemas);
//        return "rooms/newRoom/selectCinema";
//    }
//
//    @GetMapping(path = "/newRoom/selectCity/selectCinema/fillInfo")
//    public String newRoomFillInfo(
//            Model model,
//            @RequestParam(name = "cityIdKeyword", defaultValue = "") Long cityIdKeyword,
//            @RequestParam(name = "cinemaIdKeyword", defaultValue = "") Long cinemaIdKeyword
//    ) {
//        Salle room = new Salle();
//        room.setCinema(cinemaRepository.findById(cinemaIdKeyword).get());
//        model.addAttribute("room",room );
//        model.addAttribute("cityIdKeyword", cityIdKeyword);
//        model.addAttribute("cinemaIdKeyword", cinemaIdKeyword);
//
//        return "rooms/newRoom/fillInfo";
//    }
//
//    @GetMapping(path = "/saveRoom")
//    public String saveRoom(
//            @Valid Salle room, BindingResult bindingResult
//    ) {
//        if (bindingResult.hasErrors()) {
//            return "rooms/newRoom/selectCity";
//        }
//        salleRepository.save(room);
//        return "redirect:/showRoom?id="+room.getId();
//    }
//
//
//    @GetMapping(path = "/showRoom")
//    public String showRoom(
//            Model model,
//            @RequestParam(name = "id") Long id,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "15") int size,
//            @RequestParam(name = "cityNameKeyword", defaultValue = "") String cityNameKeyword,
//            @RequestParam(name = "cinemaNameKeyword", defaultValue = "") String cinemaNameKeyword,
//            @RequestParam(name = "roomNameKeyword", defaultValue = "") String roomNameKeyword
//    ) {
//        Salle room = salleRepository.findById(id).get();
//        model.addAttribute("room", room);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("cityNameKeyword", cityNameKeyword);
//        model.addAttribute("cinemaNameKeyword", cinemaNameKeyword);
//        model.addAttribute("roomNameKeyword", roomNameKeyword);
//        return "rooms/showRoom";
//    }
//
//    @GetMapping(path = "/editRoom")
//    public String editRoom(
//            Model model,
//            @RequestParam(name = "id") Long id,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "15") int size,
//            @RequestParam(name = "cityNameKeyword", defaultValue = "") String cityNameKeyword,
//            @RequestParam(name = "cinemaNameKeyword", defaultValue = "") String cinemaNameKeyword,
//            @RequestParam(name = "roomNameKeyword", defaultValue = "") String roomNameKeyword
//    ) {
//        Salle room = salleRepository.findById(id).get();
//        model.addAttribute("room", room);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("cityNameKeyword", cityNameKeyword);
//        model.addAttribute("cinemaNameKeyword", cinemaNameKeyword);
//        model.addAttribute("roomNameKeyword", roomNameKeyword);
//        return "rooms/editRoom";
//    }
//
//    @PostMapping(path = "/updateRoom")
//    public String updateRoom(@Valid Salle room, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "rooms/editRoom?id=" + room.getId();
//        }
//        Salle roomToSave = salleRepository.findById(room.getId()).get();
//        roomToSave.setName(room.getName());
//        salleRepository.save(roomToSave);
//        return "redirect:/showRoom?id=" + room.getId();
//    }
//
//}
