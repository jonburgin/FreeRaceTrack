package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.Racer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class AddRacerAction extends AbstractAction {
    private final Window parent;
    private final Racer racer;

    public AddRacerAction(Window parent, Racer racer) {
        this.parent = parent;
        this.racer = racer;
        ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("racer.new.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("racer.new.action.mnemonic.index")));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
//        RacerDialog dialog = new RacerDialog(parent, "New Racer", true);
//        dialog.setRacer(racer);
//        dialog.setVisible(true);
    }
}
