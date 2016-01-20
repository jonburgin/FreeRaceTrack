package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.Participant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class AddRacerAction extends AbstractAction {
    private final Window parent;
    private final Participant participant;

    public AddRacerAction(Window parent, Participant participant) {
        this.parent = parent;
        this.participant = participant;
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
