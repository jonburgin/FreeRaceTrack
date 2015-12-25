package net.burgin.racetrack.racing;

import net.burgin.racetrack.domain.*;

import java.util.List;

/**
 * Created by jonburgin on 12/11/15.
 */
public interface HeatGenerator {
    List<Heat> generateAllSimpleRaceHeats();

    List<Heat> generateHeatsForRunoffRace(RunoffRace runoffRace);

    List<Heat> generateHeatsForSimpleRace(SimpleRace simpleRace);
}
