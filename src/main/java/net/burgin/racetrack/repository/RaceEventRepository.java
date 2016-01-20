package net.burgin.racetrack.repository;

import net.burgin.racetrack.domain.RaceEvent;

import java.io.IOException;

/**
 * Created by jonburgin on 1/18/16.
 */
public interface RaceEventRepository {

    void save(RaceEvent raceEvent, String context) throws IOException;
    RaceEvent read(String name) throws IOException;
}
