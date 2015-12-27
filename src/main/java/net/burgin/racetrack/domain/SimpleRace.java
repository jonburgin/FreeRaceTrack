package net.burgin.racetrack.domain;

import java.util.Set;

/**
 * Created by jonburgin on 12/27/15.
 */
public interface SimpleRace extends Race {
    boolean isByClassification();
    Set<String> getCompetitionClasses();
}
