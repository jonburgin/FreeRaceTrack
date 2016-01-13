package net.burgin.racetrack.detection;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 * Created by jonburgin on 12/30/15.
 */
@Data
public class DetectionEvent {
    long time;
    List<HotSpot> hotSpots = new ArrayList<>();
    Image image = null;
    public DetectionEvent(){}
    public DetectionEvent(long time, List<HotSpot> hotSpots, Image image){
        this();
        this.time = time;
        this.hotSpots = hotSpots;
        this.image = image;
    }
}
