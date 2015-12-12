package net.burgin.racetrack.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
public class RaceEvent extends AbstractRaceParent {
    String name;
    Set<String> competitionClasses = new HashSet<>();
    List<Racer> racers = new ArrayList<>();

}
