package net.burgin.racetrack.domain;

import java.util.List;

/**
 * Created by jonburgin on 12/27/15.
 */
public interface SimpleRace extends Race {
    boolean isByClassification();
    List<String> getCompetitionClasses();
}
