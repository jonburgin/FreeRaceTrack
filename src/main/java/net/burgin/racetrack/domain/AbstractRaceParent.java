package net.burgin.racetrack.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonburgin on 12/11/15.
 */
@Data
abstract public class AbstractRaceParent extends AbstractRace implements RaceParent {
    List<RaceType> races = new ArrayList<>();

    public void addRace(RaceType race){
        races.add(race);
        raceEventChangeListeners.stream().forEach(l->race.addRaceEventChangeListener(l));
        raceAdded(this, race, races.indexOf(race));
    }

    public void removeRace(RaceType race){
        int index = races.indexOf(race);
        races.remove(index);
        raceRemoved(this, race, index);
    }

    @Override
    public int indexOf(RaceType race) {
        return races.indexOf(race);
    }
}
