package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Salle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String name;
    private int nombrePlace;
    @ToString.Exclude
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Cinema cinema;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "salle")
    private Collection<Place> places;
    @ToString.Exclude
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "salle")
    private Collection<Projection> projections;

}
