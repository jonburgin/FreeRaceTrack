package net.burgin.racetrack.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jonburgin on 12/24/15.
 */
public interface RaceResult {
    String ALL_COMPETITION_CLASSES = "ALL_COMPETITION_CLASSES";
    Map<String,List<Competitor>> getResults();
    UUID getRaceId();
    Set<String> getRaceClassifications();
}
