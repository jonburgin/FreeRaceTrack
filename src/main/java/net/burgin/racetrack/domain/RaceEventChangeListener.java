package net.burgin.racetrack.domain;

/**
 * Created by jonburgin on 12/4/15.
 */
public interface RaceEventChangeListener {

    void raceAdded(Object parent, Race child, int index);

    void raceRemoved(Object parent, Race child, int index);

    void raceChanged(Race race);
}
