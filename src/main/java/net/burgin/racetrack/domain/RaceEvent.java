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
public class RaceEvent implements RaceParent {
    String name;
    long date;
    private List<Race> races = new ArrayList<>();
    List<Car> cars = new ArrayList<>();

    private List<RaceEventChangeListener> raceEventChangeListeners = new ArrayList<>();

    public void addRaceEventChangeListener(RaceEventChangeListener raceEventChangeListener){
        raceEventChangeListeners.add(raceEventChangeListener);
    }

    public void removeRaceEventChangeListener(RaceEventChangeListener raceEventChangeListener){
        raceEventChangeListeners.remove(raceEventChangeListener);
    }

    /**
     *  is used to notify listener that a race was added to the event
     * @param parent the raceParent that had a race added to it
     * @param index the index that the race was added at
     */
    public void raceAdded(Object parent, Race child, int index) {
        raceEventChangeListeners.stream().forEach(l->l.raceAdded(parent,child,index));
    }

    /**
     * used to notify listeners that a race was removed
     * @param parent the raceParent that had the race removed
     * @param index the previous index of the removed object
     */
    public void raceRemoved(Object parent, Race child, int index) {
        raceEventChangeListeners.stream().forEach(l->l.raceRemoved(parent,child,index));
    }

    /**
     * used to notify listeners that a race has changed
     * @param race
     */
    public void raceChanged(Race race) {
        raceEventChangeListeners.stream().forEach(l->l.raceChanged(race));
    }

    public void addRace(Race race){
        races.add(race);
        race.setRaceParent(this);
        race.setRaceEvent(this);
        raceAdded(this, race, races.indexOf(race));
    }

    public void removeRace(Race race){
        int index = races.indexOf(race);
        race.setRaceEvent(null);
        race.setRaceParent(null);
        raceRemoved(this, race, index);
    }

    @Override
    public int indexOf(Race race) {
        return races.indexOf(race);
    }
}
