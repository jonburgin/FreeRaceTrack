package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.gui.FreeRaceTrack;
import net.burgin.racetrack.gui.raceEvent.RaceEventFileChooser;
import net.burgin.racetrack.repository.FileRaceEventRepository;
import net.burgin.racetrack.repository.RaceEventRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class FileOpenAction extends AbstractAction {

    private final FreeRaceTrack raceTrack;
    RaceEventRepository raceEventRepository = new FileRaceEventRepository();
    ResourceBundle resourceBundle;
    public FileOpenAction(FreeRaceTrack raceTrack){
        this.raceTrack = raceTrack;
        resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("file.open.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("file.open.action.mnemonic.index")));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser chooser = new RaceEventFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File currentDirectory = new File(System.getProperty("user.home") + File.separator + "FreeRaceTrack");
        if(!currentDirectory.exists()) {
            currentDirectory.mkdir();
        }
        chooser.setCurrentDirectory(currentDirectory);
        if(chooser.showOpenDialog(FreeRaceTrack.getInstance()) == JFileChooser.APPROVE_OPTION){

            File selectedFile = chooser.getSelectedFile();
            if(selectedFile != null){
                try {
                    String path = selectedFile.getAbsolutePath();
                    RaceEvent raceEvent = raceEventRepository.read(path);
                    raceEvent.assignVehicleIds();
                    raceTrack.addRaceEvent(raceEvent, path);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, e.getMessage(),"File read error",JOptionPane.ERROR_MESSAGE );
                }
            }
        }
    }
}
