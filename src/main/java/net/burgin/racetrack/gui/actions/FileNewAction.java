package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.gui.FreeRaceTrack;
import net.burgin.racetrack.gui.NewRaceEventDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class FileNewAction extends AbstractAction {

    public FileNewAction(){
        ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("file.new.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("file.new.action.mnemonic.index")));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        FreeRaceTrack instance = FreeRaceTrack.getInstance();
        NewRaceEventDialog dialog =
            new NewRaceEventDialog(instance, "Create Race Event", true);
        dialog.setLocationRelativeTo(instance);
     dialog.setVisible(true);
    }
}
