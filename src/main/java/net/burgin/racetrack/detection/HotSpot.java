package net.burgin.racetrack.detection;

import lombok.Data;

import java.awt.*;

/**
 * Created by jonburgin on 12/2/15.
 */
@Data
public class HotSpot {
    int lane;
    Point position;
    boolean detected;
    boolean uninitialized = true;
    int pixel;
    public HotSpot(int lane, Point position){
        this.lane = lane;
        this.position = position;
    }

    public boolean equals(Object object){
        if(!(object instanceof  HotSpot))
            return false;
        HotSpot other = (HotSpot)object;
        return position.equals(other.getPosition()) && lane == other.getLane();
    }

    public void reset(){
        uninitialized = true;
        detected = false;
    }
    public int hashCode(){
        return position.hashCode();
    }//todo need to combine lane here
}
