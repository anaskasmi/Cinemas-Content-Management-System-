package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Projection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateDeProjection;
    private double prix;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Film film;
    @OneToMany(mappedBy = "projection")
    private Collection<Ticket> tickets;
    @ManyToOne
    private Seance seance;



}
