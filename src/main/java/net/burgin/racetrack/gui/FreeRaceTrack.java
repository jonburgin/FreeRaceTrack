package net.burgin.racetrack.gui;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.gui.actions.*;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FreeRaceTrack extends JFrame implements Runnable {

    static FreeRaceTrack instance;
    ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
    JDesktopPane desktopPane = new JDesktopPane();

    public static FreeRaceTrack getInstance(){
        return instance;
    }
    @Override
    public void run() {
        setTitle("Free Race Track");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildGui();
        this.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-50), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-150));
        setVisible(true);
    }

    private void buildGui() {
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildFileMenu());
        setJMenuBar(menuBar);
        add(desktopPane, BorderLayout.CENTER);
        pack();
    }

    private JMenu buildFileMenu() {
        JMenu menu = new JMenu(new FileAction());
        menu.add(new FileNewAction());
        menu.add(new FileOpenAction(this));
        menu.add(new FileSaveAction(this));
        menu.add(new FileSaveAsAction(this));
        menu.add(new ExitAction());
        return  menu;
    }

    public static void main(String[] args) {
        instance = new FreeRaceTrack();
        SwingUtilities.invokeLater(instance);
    }

    public void addRaceEvent(RaceEvent raceEvent, String path) {
        EventInternalFrame eventInternalFrame = new EventInternalFrame(raceEvent);
        eventInternalFrame.setFilePath(path);
        eventInternalFrame.setTitle(raceEvent.getName());
        eventInternalFrame.setSize(this.getContentPane().getSize());
        desktopPane.add(eventInternalFrame);
        eventInternalFrame.setVisible(true);
    }
}
