package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cinema cinema;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "salle", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Collection<Place> places;
    @ToString.Exclude
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "salle", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Collection<Projection> projections;

}
