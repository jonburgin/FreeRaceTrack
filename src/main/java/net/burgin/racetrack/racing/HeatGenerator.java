package net.burgin.racetrack.racing;

import net.burgin.racetrack.domain.*;

import java.util.List;

/**
 * Created by jonburgin on 12/11/15.
 */
public interface HeatGenerator {
    List<Heat> generateRaceHeats(RaceEvent raceEvent);

    List<Heat> generateHeat(RaceType race, List<Car> cars, Track track);
}
