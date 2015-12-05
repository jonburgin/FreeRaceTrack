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
public class FileOpenAction extends AbstractAction {

    ResourceBundle resourceBundle;
    public FileOpenAction(){
        resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("file.open.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("file.open.action.mnemonic.index")));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(resourceBundle.getString("file.type.description"),resourceBundle.getString("file.type.extension"));
        chooser.setFileFilter(filter);
        if(chooser.showOpenDialog(FreeRaceTrack.getInstance()) == JFileChooser.APPROVE_OPTION){
            System.out.println("open " + chooser.getSelectedFile().getName());
        }
    }
}
