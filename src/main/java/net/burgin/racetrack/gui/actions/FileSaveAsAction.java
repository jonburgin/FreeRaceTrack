package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.gui.FreeRaceTrack;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class FileSaveAsAction extends FileSaveAction {

    ResourceBundle resourceBundle;
    public FileSaveAsAction(FreeRaceTrack raceTrack){
        super(raceTrack);
        resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("file.saveas.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("file.saveas.action.mnemonic.index")));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        super.saveToFile(false);
    }
}
