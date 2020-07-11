package org.sid.cinema.dao;

import org.sid.cinema.entities.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin("*")
public interface CinemaRepository extends JpaRepository<Cinema,Long> {
    public Page<Cinema> findById(Long idKeyword, Pageable pageable);
    public Page<Cinema> findByNameContains(String nameKeyword, Pageable pageable);
    public Page<Cinema> findByVilleNameContains(String cityKeyword, Pageable pageable);
    public List<Cinema> findByVille_Id(Long cityIdKeyword);


}
