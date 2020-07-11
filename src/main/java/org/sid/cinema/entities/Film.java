package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Film {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String titre;
    private String descritption;
    @Column(length = 30)
    private String realisiateur;
    private Date dateSortie;
    private double duree;
    private String photo;
    @ManyToOne
    private Categorie categorie;
    @OneToMany(mappedBy = "film", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Collection<Projection> projections;

}
