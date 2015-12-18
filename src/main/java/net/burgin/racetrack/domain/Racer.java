package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/4/15.
 */

@Getter
@Setter
@EqualsAndHashCode(exclude="image")
public class Racer {
    UUID uuid = UUID.randomUUID();
    String firstName;
    String lastName;
    List<Car> cars = new ArrayList<>();
    @JsonIgnore
    Image image;
    public String toString(){
        return cars.stream()
                .map(c->c.getName())
                .collect(Collectors.joining(",", firstName + " " + lastName + " (", ")")).toString();
    }

    public Racer(){}

    public Racer(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
