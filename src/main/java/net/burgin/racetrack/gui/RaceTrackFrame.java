package net.burgin.racetrack.gui;

import com.github.sarxos.webcam.*;
import net.burgin.racetrack.detection.HotSpotDetector;
import net.burgin.racetrack.detection.OneTimeHotSpotDetector;
import net.burgin.racetrack.gui.adapters.WebcamAdapter;
import net.burgin.racetrack.gui.heats.RacetrackWebcamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by jonburgin on 11/13/15.
 */
public class RaceTrackFrame extends JFrame implements Runnable,
        Thread.UncaughtExceptionHandler, ItemListener,WebcamDiscoveryListener{

    private static final long serialVersionUID = 1L;

    private Webcam webcam = null;
    private RacetrackWebcamPanel panel = null;
    private WebcamPicker picker = null;
    private DefaultWebCamListener webCamListener = new DefaultWebCamListener();
    private WindowListener defaultWindowListener = new DefaultWindowListener();

    @Override
    public void run() {
        Webcam.addDiscoveryListener(this);
        setTitle("Java Webcam Capture POC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        addWindowListener(defaultWindowListener);
        picker = new WebcamPicker();
        picker.setSelectedIndex(picker.getItemCount() - 1);
        picker.addItemListener(this);
        webcam = picker.getSelectedWebcam();
        if (webcam == null) {
            System.out.println("No webcams found...");
            System.exit(1);
        }
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.addWebcamListener(webCamListener);
        panel = new RacetrackWebcamPanel(webcam, false);
        panel.setFitArea(true);
        panel.setFPSDisplayed(true);
        panel.setDisplayLanes(true);
        add(picker, BorderLayout.NORTH);
        add(this.panel, BorderLayout.CENTER);
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(70,1,100,2));
        spinner.addChangeListener((event)->((OneTimeHotSpotDetector)panel.getHotSpotDetector()).setSensitivity((Integer)((JSpinner)event.getSource()).getValue()));
        JButton button = new JButton("Reset");
        button.addActionListener((event)->panel.getHotSpotDetector().reset());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        buttonPanel.add(spinner);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        Thread t = new Thread(()->{
                webcam.open(true);
                RaceTrackFrame.this.panel.start();
            });
        t.setName("example-starter");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RaceTrackFrame());
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(String.format("Exception in thread %s", t.getName()));
        e.printStackTrace();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if ((e.getItem() != webcam) && (webcam != null) ){
            panel.stop();
            remove(panel);
            webcam.removeWebcamListener(webCamListener);
            webcam.close();
            webcam = (Webcam) e.getItem();
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcam.addWebcamListener(webCamListener);
            System.out.println("selected " + webcam.getName());
            panel = new RacetrackWebcamPanel(webcam, false);
            panel.setFPSDisplayed(true);
            add(panel, BorderLayout.CENTER);
            pack();
            Thread t = new Thread(()-> panel.start());
            t.setName("example-stoper");
            t.setDaemon(true);
            t.setUncaughtExceptionHandler(this);
            t.start();
        }
    }

    @Override
    public void webcamFound(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.addItem(event.getWebcam());
        }
    }

    @Override
    public void webcamGone(WebcamDiscoveryEvent event) {
        if (picker != null) {
            picker.removeItem(event.getWebcam());
        }
    }

    class DefaultWindowListener extends WindowAdapter{

        @Override
        public void windowClosed(WindowEvent e) {
            webcam.close();
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            System.out.println("webcam viewer resumed");
            panel.resume();
        }

        @Override
        public void windowIconified(WindowEvent e) {
            System.out.println("webcam viewer paused");
            panel.pause();
        }
    }

    class DefaultWebCamListener extends WebcamAdapter {

        @Override
        public void webcamOpen(WebcamEvent we) {
            System.out.println("webcam open");
        }

        @Override
        public void webcamClosed(WebcamEvent we) {
            System.out.println("webcam closed");
        }

        @Override
        public void webcamDisposed(WebcamEvent we) {
            System.out.println("webcam disposed");
        }

    }
}
