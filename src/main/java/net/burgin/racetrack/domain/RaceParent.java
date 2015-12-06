package net.burgin.racetrack.domain;

import java.util.List;

/**
 * Created by jonburgin on 12/4/15.
 */
public interface RaceParent {
    /**
     *
     * @param race that you want to know index of
     * @return -1 if the race is not a child
     */
    int indexOf(Race race);

    List<Race> getRaces();
}
