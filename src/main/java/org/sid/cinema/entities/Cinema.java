package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Cinema implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;
    private int nombreSalles;
    @OneToMany(mappedBy ="cinema")
    private Collection<Salle> salles;
    @ManyToOne
    private Ville ville;
}