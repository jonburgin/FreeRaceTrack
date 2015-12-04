package net.burgin.racetrack.gui;

import com.github.sarxos.webcam.*;
import net.burgin.racetrack.detection.HotSpotDetector;
import net.burgin.racetrack.detection.OneTimeHotSpotDetector;
import net.burgin.racetrack.gui.adapters.WebcamAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by jonburgin on 11/13/15.
 */
public class RaceTrackGui extends JFrame implements Runnable,
        Thread.UncaughtExceptionHandler, ItemListener,WebcamDiscoveryListener{

    private static final long serialVersionUID = 1L;

    private Webcam webcam = null;
    private RacetrackWebcamPanel panel = null;
    private WebcamPicker picker = null;
    private HotSpotDetector hotSpotDetector = new OneTimeHotSpotDetector();;
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
        panel.setFPSDisplayed(true);

        panel.setHotSpotDetector(hotSpotDetector);
        //panel.setMirrored(true);
        panel.setDisplayLanes(true);
        add(picker, BorderLayout.NORTH);
        add(this.panel, BorderLayout.CENTER);
        JButton button = new JButton("Reset");
        add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ((OneTimeHotSpotDetector)hotSpotDetector).reset();
            }
        });

        pack();
        setVisible(true);

        Thread t = new Thread() {

            @Override
            public void run() {
                webcam.open(true);
                RaceTrackGui.this.panel.start();
            }
        };
        t.setName("example-starter");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RaceTrackGui());
    }



    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(String.format("Exception in thread %s", t.getName()));
        e.printStackTrace();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() != webcam) {
            if (webcam != null) {
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
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        panel.start();
                    }
                };
                t.setName("example-stoper");
                t.setDaemon(true);
                t.setUncaughtExceptionHandler(this);
                t.start();
            }
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

    class DefaultWindowListener implements WindowListener{

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
            webcam.close();
        }

        @Override
        public void windowClosing(WindowEvent e) {
        }

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
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
