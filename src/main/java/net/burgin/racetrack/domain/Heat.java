package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/2/15.
 *
 * This class represent a particular run within a race
 * competitors position within the list corresponds to their lane number
 */
@Data
@EqualsAndHashCode(exclude = "uuid")
public class Heat {
    List<Competitor> competitors;
    UUID uuid = UUID.randomUUID();//for saving the photofinish
    String name;
    long startTime;
    @JsonIgnore
    List<Image> photofinish;

    public Heat(Race race, List<Vehicle> vehicles){
        name = race.getName();
        competitors = vehicles.stream()
            .map(car -> new Competitor(car))
            .collect(Collectors.toList());
    }

    public String toString(){
        return competitors.stream()
                .map(competitor -> competitor.getVehicle().getName())
                .collect(Collectors.joining("', '", name + " ['", "']"));
    }
}
