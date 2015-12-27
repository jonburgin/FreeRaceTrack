package net.burgin.racetrack.domain;

import java.util.List;

/**
 * Created by jonburgin on 12/27/15.
 */
public interface RunoffRace extends RaceParent, Race{
    int getTakeNumber();
    boolean childrenNotReady();
    List<Vehicle> getVehicles();
}
