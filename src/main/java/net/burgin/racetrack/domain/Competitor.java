package net.burgin.racetrack.domain;

import lombok.Data;
import net.burgin.racetrack.domain.Car;

/**
 * Created by jonburgin on 12/2/15.
 *
 * This class represents the results of an individual cars performance in a heat
 */
@Data
public class Competitor {
    Car car;
    long raceTime;
    double averageTime;

    public Competitor(Car car){
        this.car = car;
    }
}
