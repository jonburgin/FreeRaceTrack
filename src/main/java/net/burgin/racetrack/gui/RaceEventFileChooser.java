package net.burgin.racetrack.gui;

import net.burgin.racetrack.RaceTrackResourceBundle;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 1/18/16.
 */
public class RaceEventFileChooser extends JFileChooser {
    ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
    public RaceEventFileChooser() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(resourceBundle.getString("file.type.description"),resourceBundle.getString("file.type.extension"));
        setFileFilter(filter);
    }

}
