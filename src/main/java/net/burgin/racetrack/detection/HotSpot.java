package net.burgin.racetrack.detection;

import lombok.Data;
import lombok.NonNull;

import java.awt.*;

/**
 * Created by jonburgin on 12/2/15.
 */
@Data
public class HotSpot {
    @NonNull
    int lane;
    Point position;
    boolean detected;
    boolean needsInitialization = true;
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
        needsInitialization = true;
        detected = false;
    }
    public int HashCode(){
        return position.hashCode();
    }//todo need to combine lane here
}
