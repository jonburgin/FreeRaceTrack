package net.burgin.racetrack.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonburgin on 12/4/15.
 */
@Getter
@Setter
public class RaceEvent extends AbstractRaceEventChangeNotifier implements RaceParent  {
    String name;
    long date;
    private List<Race> races = new ArrayList<>();
    List<String> classes = new ArrayList<>();
    List<Racer> racers = new ArrayList<>();

    public void addRace(Race race){
        races.add(race);
        raceEventChangeListeners.stream().forEach(l->race.addRaceEventChangeListener(l));
        raceAdded(this, race, races.indexOf(race));
    }

    public void removeRace(Race race){
        int index = races.indexOf(race);
        raceRemoved(this, race, index);
    }

    @Override
    public int indexOf(Race race) {
        return races.indexOf(race);
    }
}
