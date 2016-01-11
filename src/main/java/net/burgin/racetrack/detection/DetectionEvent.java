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
    List<HotSpot> hotSpots = new ArrayList<>();
    Image image = null;
    public DetectionEvent(){}
    public DetectionEvent(List<HotSpot> hotSpots, Image image){
        this();
        this.hotSpots = hotSpots;
        this.image = image;
    }
}
