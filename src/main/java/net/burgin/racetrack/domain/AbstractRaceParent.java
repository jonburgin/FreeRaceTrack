package net.burgin.racetrack.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonburgin on 12/11/15.
 */
@Data
@EqualsAndHashCode(callSuper=true)
abstract public class AbstractRaceParent extends AbstractRace implements RaceParent {
    List<Race> races = new ArrayList<>();

    public void addRace(Race race){
        races.add(race);
        raceEventChangeListeners.stream().forEach(l->race.addRaceEventChangeListener(l));
        raceAdded(this, race, races.indexOf(race));
    }

    public void removeRace(Race race){
        int index = races.indexOf(race);
        races.remove(index);
        raceRemoved(this, race, index);
    }

    @Override
    public int indexOf(Race race) {
        return races.indexOf(race);
    }

}
