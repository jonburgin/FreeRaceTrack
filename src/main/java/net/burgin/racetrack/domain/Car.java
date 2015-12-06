package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.awt.*;

/**
 * Created by jonburgin on 12/2/15.
 */
@Data
public class Car {
    String name;
    @JsonIgnore
    Racer racer;
    String competitionClass;
    @JsonIgnore
    Image image;
}
