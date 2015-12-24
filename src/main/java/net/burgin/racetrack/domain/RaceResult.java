package net.burgin.racetrack.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jonburgin on 12/24/15.
 */
@Data
public class RaceResult {
    final static String ALL_COMPETITION_CLASSES = "ALL_COMPETITION_CLASSES";
    Map<String,List<Competitor>> results = new HashMap<>();
}
