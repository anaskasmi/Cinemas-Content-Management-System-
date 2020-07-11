package org.sid.cinema.dao;

import org.sid.cinema.entities.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")

public interface SalleRepository extends JpaRepository<Salle,Long> {
//    public Page<Salle> findByCinema_Ville_NameIgnoreCaseContaining(String cityNameKeyword, Pageable pageable);
//    public Page<Salle> findByCinema_NameIgnoreCaseContaining(String cinemaNameKeyword, Pageable pageable);
    public Page<Salle> findByCinema_Ville_NameIgnoreCaseContainingAndCinema_NameIgnoreCaseContaining(String cityNameKeyword,String cinemaNameKeyword, Pageable pageable);
    public Page<Salle> findByCinema_Ville_NameIgnoreCaseContainingAndCinema_NameAndNameIgnoreCaseContaining(String cityNameKeyword,String cinemaNameKeyword,String roomNameKeyword, Pageable pageable);


}
