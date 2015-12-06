package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
public class Racer {
    String firstName;
    String lastName;
    List<Car> cars = new ArrayList<>();
    @JsonIgnore
    Image image;
    public String toString(){
        return cars.stream().map(c->c.getName()).collect(Collectors.joining(",", firstName + " " + lastName + " (", ")")).toString();

    }
}
