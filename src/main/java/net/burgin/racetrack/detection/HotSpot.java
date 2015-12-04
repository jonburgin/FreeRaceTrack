package net.burgin.racetrack.detection;

import java.awt.*;

/**
 * Created by jonburgin on 12/2/15.
 */
public class HotSpot {
    public Point position;
    public boolean detected;
    public boolean first = true;
    public int pixel;
    public HotSpot(Point position){
        this.position = position;
    }
}
