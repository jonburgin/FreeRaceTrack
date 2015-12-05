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
public class FileSaveAsAction extends AbstractAction {

    ResourceBundle resourceBundle;
    public FileSaveAsAction(){
        resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("file.saveas.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("file.saveas.action.mnemonic.index")));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(resourceBundle.getString("file.type.description"),resourceBundle.getString("file.type.extension"));
        chooser.setFileFilter(filter);
        if(chooser.showSaveDialog(FreeRaceTrack.getInstance()) == JFileChooser.APPROVE_OPTION){
            System.out.println("Save As " + chooser.getSelectedFile().getName());
            //// TODO: 12/4/15
        }
    }
}
