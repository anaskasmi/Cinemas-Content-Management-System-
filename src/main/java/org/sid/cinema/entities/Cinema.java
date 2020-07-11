package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Cinema implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    @NotNull
    @Size(min=1,max = 29)
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;
    @NotNull
    @Min(0)
    private int nombreSalles;
    @ToString.Exclude
    @OneToMany(mappedBy ="cinema", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Collection<Salle> salles;
    @NotNull
    @ToString.Exclude
    @ManyToOne
    private Ville ville;
}