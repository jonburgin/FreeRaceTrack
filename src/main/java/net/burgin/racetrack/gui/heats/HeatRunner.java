package net.burgin.racetrack.gui.heats;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import net.burgin.racetrack.detection.DetectionEvent;
import net.burgin.racetrack.domain.Heat;
import net.burgin.racetrack.domain.RaceEvent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jonburgin on 1/19/16.
 */
public class HeatRunner {

    final static String prepLabel = "prep";
    final static String webcamLabel = "race";
    final static String replayLabel = "replay";
    JFrame frame = new JFrame("Heat Runner");//todo resource
    CardLayout cardLayout = new CardLayout();
    Container contentPane;
    PrepPanel prepPanel;
    RacetrackWebcamPanel webcamPanel;
    Heat heat;
    private ReplayPanel replayPanel;


    public HeatRunner(RaceEvent raceEvent, Heat heat){
        buildGui(raceEvent, heat);
    }

    private void buildGui(RaceEvent raceEvent, Heat heat) {
        this.heat = heat;
        contentPane = frame.getContentPane();
        contentPane.setLayout(cardLayout);
        prepPanel = new PrepPanel(raceEvent, heat);
        contentPane.add(buildPrepPanel(raceEvent,heat),prepLabel);
        java.util.List<Webcam> webcams = Webcam.getWebcams();
        Webcam webcam = webcams.get(webcams.size()-1);
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcamPanel = new RacetrackWebcamPanel(webcam);
        webcamPanel.setFitArea(true);
        webcamPanel.getHotSpotTrack().setLaneCount(raceEvent.getTrack().getLaneCount());
        contentPane.add(webcamPanel, webcamLabel);
        contentPane.add(replayPanel = new ReplayPanel(heat), replayLabel);
        //todo add window listener
        frame.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-50), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-150)));
        frame.setSize(frame.getPreferredSize());
        frame.setVisible(true);
    }

    private void startCountDown(){
        System.out.println("Starting countdown");
        cardLayout.show(contentPane,webcamLabel);
        webcamPanel.getHotSpotDetector().addHotSpotListener(heat);
        webcamPanel.getHotSpotDetector().addHotSpotListener(e->handleDetectionEvent(e));
        webcamPanel.doCountDown();
    }
    private void handleDetectionEvent(DetectionEvent event) {
        if(event.getHotSpots().size() >= heat.getCompetitors().size()+1)
            doReplay();
    }

    private void doReplay() {
        cardLayout.show(contentPane,replayLabel);
        replayPanel.doReplay();
    }

    private Component buildPrepPanel(RaceEvent raceEvent, Heat heat) {
        JPanel panel = new JPanel(new BorderLayout());
        prepPanel = new PrepPanel(raceEvent, heat);
        panel.add(prepPanel, BorderLayout.CENTER);
        JButton button = new JButton("Start");
        button.addActionListener((e)->startCountDown());
        button.setFont(button.getFont().deriveFont(20f));
        panel.add(button, BorderLayout.SOUTH);
        return panel;
    }

    public void run(RaceEvent raceEvent, Heat heat) {
        webcamPanel.getHotSpotDetector().removeHotSpotListener(heat);

        this.heat = heat;
        webcamPanel.getHotSpotDetector().addHotSpotListener(heat);

        frame.setVisible(true);
        heat.reset();
        prepPanel.rebuild(raceEvent, heat);
        replayPanel.setHeat(heat);
        cardLayout.first(contentPane);
    }



}
