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
    List<RaceType> raceTypes = new ArrayList<>();

    public void addRace(RaceType race){
        raceTypes.add(race);
        raceEventChangeListeners.stream().forEach(l->race.addRaceEventChangeListener(l));
        raceAdded(this, race, raceTypes.indexOf(race));
    }

    public void removeRace(RaceType race){
        int index = raceTypes.indexOf(race);
        raceTypes.remove(index);
        raceRemoved(this, race, index);
    }

    @Override
    public int indexOf(RaceType race) {
        return raceTypes.indexOf(race);
    }

}
