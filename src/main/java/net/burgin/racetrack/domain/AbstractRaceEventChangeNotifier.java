package net.burgin.racetrack.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonburgin on 12/5/15.
 */
abstract public class AbstractRaceEventChangeNotifier implements RaceEventChangeNotifier{

    protected List<RaceEventChangeListener> raceEventChangeListeners = new ArrayList<>();

    public void addRaceEventChangeListener(RaceEventChangeListener raceEventChangeListener){
        raceEventChangeListeners.add(raceEventChangeListener);
        if(this instanceof RaceParent){
            ((RaceParent)this).getRaceTypes().stream()
                    .forEach(race -> race.addRaceEventChangeListener(raceEventChangeListener));
        }
    }

    public void removeRaceEventChangeListener(RaceEventChangeListener raceEventChangeListener){
        raceEventChangeListeners.remove(raceEventChangeListener);
        if(this instanceof RaceParent) {
            ((RaceParent) this).getRaceTypes().stream()
                    .forEach(race -> race.removeRaceEventChangeListener(raceEventChangeListener));
        }
    }

    /**
     *  is used to notify listener that a race was added to the event
     * @param parent the raceParent that had a race added to it
     * @param index the index that the race was added at
     */
    public void raceAdded(Object parent, RaceType child, int index) {
        raceEventChangeListeners.stream()
                .forEach(l->l.raceAdded(parent,child,index));
    }

    /**
     * used to notify listeners that a race was removed
     * @param parent the raceParent that had the race removed
     * @param index the previous index of the removed object
     */
    public void raceRemoved(Object parent, RaceType child, int index) {
        raceEventChangeListeners.stream()
                .forEach(l->l.raceRemoved(parent,child,index));
    }

    /**
     * used to notify listeners that a race has changed
     * @param race
     */
    public void raceChanged(RaceType race) {
        raceEventChangeListeners.stream()
                .forEach(l->l.raceChanged(race));
    }

}
