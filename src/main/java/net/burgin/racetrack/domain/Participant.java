package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/4/15.
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class Participant extends AbstractImageHolder{
    String firstName;
    String lastName;
    List<Vehicle> vehicles = new ArrayList<>();

    public String toString(){
        return vehicles.stream()
                .map(c->c.getName())
                .collect(Collectors.joining(",", firstName + " " + lastName + " (", ")")).toString();
    }

    public Participant(){}

    public Participant(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
