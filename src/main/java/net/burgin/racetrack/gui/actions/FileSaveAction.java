package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.gui.EventInternalFrame;
import net.burgin.racetrack.gui.FreeRaceTrack;
import net.burgin.racetrack.gui.RaceEventFileChooser;
import net.burgin.racetrack.repository.FileRaceEventRepository;
import net.burgin.racetrack.repository.RaceEventRepository;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class FileSaveAction extends AbstractAction {

    private final FreeRaceTrack raceTrack;
    private ResourceBundle resourceBundle;
    private RaceEventFileChooser raceEventFileChooser = new RaceEventFileChooser();
    private RaceEventRepository repository = new FileRaceEventRepository();
    public FileSaveAction(FreeRaceTrack raceTrack){
        this.raceTrack = raceTrack;
        resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("file.save.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("file.save.action.mnemonic.index")));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        saveToFile(true);
    }

    protected void saveToFile(boolean useStoredPath){
        EventInternalFrame selectedFrame = (EventInternalFrame) raceTrack.getDesktopPane().getSelectedFrame();
        if(selectedFrame == null)
            return;//todo fix this crap
        RaceEvent raceEvent = selectedFrame.getRaceEvent();
        String path = useStoredPath?selectedFrame.getFilePath():null;
        if(path == null){
            raceEventFileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "directory";
                }
            });
            raceEventFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = raceEventFileChooser.showSaveDialog(raceTrack);
            if(option != JFileChooser.APPROVE_OPTION)
                return;
            path = raceEventFileChooser.getSelectedFile().getAbsolutePath();
            path += File.separator + raceEvent.getName() + raceEvent.getId();
            selectedFrame.setFilePath(path);
        }
        try {
            repository.save(raceEvent,path);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(raceTrack, e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
