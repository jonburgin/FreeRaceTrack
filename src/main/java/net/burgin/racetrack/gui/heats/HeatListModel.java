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
    public HeatListModel(RaceEvent raceEvent) {
        this.raceEvent = raceEvent;
    }

    @Override
    public int getSize() {
        return raceEvent.getHeats().size();
    }

    @Override
    public Heat getElementAt(int i) {
        List<Heat> heats = raceEvent.getHeats();
        return heats.get(i);
    }

    public void generateHeats() {
        raceEvent.generateHeats();
        this.fireContentsChanged(this,0,getSize());
    }
}
