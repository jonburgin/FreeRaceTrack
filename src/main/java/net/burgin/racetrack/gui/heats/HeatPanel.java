package net.burgin.racetrack.gui.heats;

import net.burgin.racetrack.domain.Heat;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.gui.actions.GenerateRaceHeatsAction;
import net.burgin.racetrack.gui.actions.GenerateRunoffHeatsAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jonburgin on 12/24/15.
 */
public class HeatPanel extends JPanel {
    private final RaceEvent raceEvent;
    HeatListModel model;

    public HeatPanel(RaceEvent raceEvent) {
        this.raceEvent = raceEvent;
        model = new HeatListModel(raceEvent);
        buildGui();
    }

    private void buildGui() {
        setLayout(new BorderLayout());
        add(buildToolBar(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
    }

    private Component buildToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(new GenerateRaceHeatsAction(model));
        toolBar.add(new GenerateRunoffHeatsAction(model));
        return toolBar;
    }

    private Component buildCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JList<Heat> jList = new JList<>(model);
        panel.add(new JScrollPane(jList));

        return panel;
    }
}
