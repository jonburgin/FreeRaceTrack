package net.burgin.racetrack.domain;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/24/15.
 */
public interface RaceResult {
    Race getRace();
    String getName();
    List<Competitor> getCompetitors();

}
