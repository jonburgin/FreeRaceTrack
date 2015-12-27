package net.burgin.racetrack.domain;

import lombok.Data;
import net.burgin.racetrack.domain.Vehicle;

/**
 * Created by jonburgin on 12/2/15.
 *
 * This class represents the competitors of an individual cars performance in a heat
 */
@Data
public class Competitor {
    Vehicle vehicle;
    long raceTime;
    double averageTime;

    public Competitor(Vehicle vehicle){
        this.vehicle = vehicle;
    }
}
