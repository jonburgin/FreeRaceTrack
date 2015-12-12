package net.burgin.racetrack.racing;

import net.burgin.racetrack.domain.Car;
import net.burgin.racetrack.domain.Heat;
import net.burgin.racetrack.domain.RaceType;
import net.burgin.racetrack.domain.Track;

import java.util.List;

/**
 * Created by jonburgin on 12/11/15.
 */
public interface HeatGenerator {
    List<Heat> generate(RaceType race, List<Car> cars, Track track);
}
