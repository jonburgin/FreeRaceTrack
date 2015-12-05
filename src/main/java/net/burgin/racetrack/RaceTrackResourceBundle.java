package net.burgin.racetrack;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class RaceTrackResourceBundle {
    static private ResourceBundle instance;
    synchronized public static ResourceBundle getInstance(){
        if (instance == null){
            instance = ResourceBundle.getBundle("default", Locale.getDefault(),ClassLoader.getSystemClassLoader());
        }
        return instance;
    }
}
