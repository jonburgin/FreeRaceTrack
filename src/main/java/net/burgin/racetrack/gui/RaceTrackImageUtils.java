package net.burgin.racetrack.gui;

import net.burgin.racetrack.domain.Racer;
import net.burgin.racetrack.domain.Vehicle;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jonburgin on 1/6/16.
 */
public class RaceTrackImageUtils {

    public static ImageIcon getImage(Racer racer) {
        return new ImageIcon(ClassLoader.getSystemClassLoader().getSystemResource("images/duke.gif"));
    }

    public static ImageIcon getImage(Vehicle vehicle) {
        return new ImageIcon(ClassLoader.getSystemClassLoader().getSystemResource("images/pinewood-derby2.jpg"));
    }
}
