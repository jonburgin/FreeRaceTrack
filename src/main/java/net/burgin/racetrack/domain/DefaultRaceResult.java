package net.burgin.racetrack.domain;

import lombok.Data;

import java.util.*;

/**
 * Created by jonburgin on 12/24/15.
 */
@Data
public class DefaultRaceResult implements RaceResult{
    Map<String,List<Competitor>> results = new HashMap<>();
    UUID raceId;

    @Override
    public Set<String> getRaceClassifications() {
        Set<String> strings = new HashSet(results.keySet());
        strings.remove(ALL_COMPETITION_CLASSES);
        return strings;
    }
}
