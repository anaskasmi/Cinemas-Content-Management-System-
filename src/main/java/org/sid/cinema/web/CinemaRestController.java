package org.sid.cinema.web;

import lombok.Data;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin("*")
@RestController
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws Exception {
        System.out.println(System.getProperty("user.home"));

        Film f = filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        File file = new File( "images/" + photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public boolean payerTickets(@RequestBody TicketFrom ticketFrom) {
        List<Ticket> listTickets = new ArrayList<>();
        ticketFrom.getTickets().forEach(idTicket -> {
            Ticket ticket = ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketFrom.getNomClient());
            ticket.setReserve(true);
            ticket.setCodePayement(ticketFrom.getCodePayement());
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
        return true ;
    }
}

