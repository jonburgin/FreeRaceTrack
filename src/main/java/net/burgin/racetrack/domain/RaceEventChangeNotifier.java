package net.burgin.racetrack.domain;

/**
 * Created by jonburgin on 12/11/15.
 */
public interface RaceEventChangeNotifier {
    void addRaceEventChangeListener(RaceEventChangeListener raceEventChangeListener);
    void removeRaceEventChangeListener(RaceEventChangeListener raceEventChangeListener);

    /**
     *  is used to notify listener that a race was added to the event
     * @param parent the raceParent that had a race added to it
     * @param index the index that the race was added at
     */
    void raceAdded(Object parent, RaceType child, int index);

    /**
     * used to notify listeners that a race was removed
     * @param parent the raceParent that had the race removed
     * @param index the previous index of the removed object
     */
    void raceRemoved(Object parent, RaceType child, int index);

    /**
     * used to notify listeners that a race has changed
     * @param race
     */
    void raceChanged(RaceType race);
}
