package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CinemaFormPayload {
    private Long id;
    @NotNull
    @Size(min=1,max = 29)
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;
    @NotNull
    @Min(0)
    private int nombreSalles;
    @NotNull
    private Long cityId;
}
