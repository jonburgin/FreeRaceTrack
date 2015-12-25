package net.burgin.racetrack.gui.heats;

import net.burgin.racetrack.domain.Heat;
import net.burgin.racetrack.domain.RaceEvent;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/24/15.
 */
public class HeatListModel extends DefaultListModel<Heat> {
    RaceEvent raceEvent;
   // ArrayList<ListDataListener> dataListeners = new ArrayList<>();
    public HeatListModel(RaceEvent raceEvent) {
        this.raceEvent = raceEvent;
    }

    @Override
    public int getSize() {
        long count = raceEvent.getHeats().values().stream()
                .flatMap(l -> l.stream())
                .count();
        return (int)count;
    }

    @Override
    public Heat getElementAt(int i) {
        List<Heat> heats = raceEvent.getHeats().values().stream()
                .flatMap(l -> l.stream())
                .collect(Collectors.toList());
        return heats.get(i);

    }

    public void putAll(Map<UUID, List<Heat>> heatsToAdd) {
        raceEvent.getHeats().putAll(heatsToAdd);//todo probably shouldn't overwrite here
        this.fireIntervalAdded(this, 0, getSize());
    }

    public RaceEvent getRaceEvent() {
        return raceEvent;
    }
}
