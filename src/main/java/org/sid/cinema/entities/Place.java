package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private double longitude;
    private double latitude;
    private double altitude;
    @ManyToOne
    private Salle salle;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "place", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Collection<Ticket> tickets;

}
