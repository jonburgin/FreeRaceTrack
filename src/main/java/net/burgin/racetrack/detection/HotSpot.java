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
    public Point position;
    public boolean detected;
    public boolean needsInitialization = true;
    public int pixel;
    public HotSpot(Point position){
        setPosition(position);
    }

    public boolean equals(Object object){
        return (object instanceof HotSpot) && (((HotSpot)object).getPosition().equals(position));
    }

    public void reset(){
        needsInitialization = true;
        detected = false;
    }
    public int HashCode(){
        return position.hashCode();
    }
}
