package org.sid.cinema.dao;

import org.sid.cinema.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")

public interface PlaceRepository extends JpaRepository<Place,Long> {
    

}
