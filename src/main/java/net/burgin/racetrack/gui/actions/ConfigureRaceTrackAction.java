package net.burgin.racetrack.gui.actions;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.gui.heats.RacetrackWebcamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 1/21/16.
 */
public class ConfigureRaceTrackAction extends AbstractAction {

    Window window;

    public ConfigureRaceTrackAction(Window window) {
        this.window = window;
        ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("configure.racetrack.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("configure.racetrack.action.mnemonic.index")));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFrame frame = new JFrame("Configure Track");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(6,1,20,1));
        contentPane.add(spinner,BorderLayout.NORTH);
        java.util.List<Webcam> webcams = Webcam.getWebcams();
        Webcam webcam = webcams.get(webcams.size()-1);
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        RacetrackWebcamPanel webcamPanel = new RacetrackWebcamPanel(webcam);
        contentPane.add(webcamPanel,BorderLayout.CENTER);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                webcam.close();
            }
        });
        frame.setVisible(true);
    }
}
