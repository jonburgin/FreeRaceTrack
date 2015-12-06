package net.burgin.racetrack.gui;

import net.burgin.racetrack.domain.RaceEvent;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * Created by jonburgin on 12/5/15.
 */
public class RaceEventListPanel extends JPanel {
    private Function<RaceEvent,List> listGetter;

    public RaceEventListPanel(RaceEvent raceEvent, Function<RaceEvent,List> listGetter){
        this.listGetter = listGetter;
        setLayout(new BorderLayout());
        JList list = new JList(new RacerListModel(raceEvent));
        this.add(new JScrollPane(list), BorderLayout.CENTER);
    }

    class RacerListModel extends AbstractListModel{

        RaceEvent raceEvent;

        public RacerListModel(RaceEvent raceEvent) {
            this.raceEvent = raceEvent;
        }

        @Override
        public int getSize() {
            return listGetter.apply(raceEvent).size();
        }

        @Override
        public Object getElementAt(int i) {
            return listGetter.apply(raceEvent).get(i);
        }
    }
}
