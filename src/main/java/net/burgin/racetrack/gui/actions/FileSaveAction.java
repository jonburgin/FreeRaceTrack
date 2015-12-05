package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class FileSaveAction extends AbstractAction {

    ResourceBundle resourceBundle;
    public FileSaveAction(){
        resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("file.save.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("file.save.action.mnemonic.index")));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //todo
    }
}
