package net.burgin.racetrack.detection;

import com.github.sarxos.webcam.WebcamEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.gui.adapters.WebcamAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by jonburgin on 11/15/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OneTimeHotSpotDetector extends WebcamAdapter implements HotSpotDetector {

    int sensitivity = 50;
    Map<Point,HotSpot> laneHotSpots = new HashMap<Point,HotSpot>();
    int detectedHotSpots = 0;
    BufferedImage image;
    List <HotSpotListener> hotSpotListeners = new ArrayList<>();
    boolean enabled = true;

    public void addHotSpotPoint(Point point){
        if(!laneHotSpots.containsKey(point))
            laneHotSpots.put(point, new HotSpot(point));
    }

    public void removeHotSpots(){
        laneHotSpots.clear();
    }

    public void reset(){
        System.out.println("resetting");
        detectedHotSpots = 0;
        for(HotSpot hotSpot: laneHotSpots.values()){
            hotSpot.detected = false;
            hotSpot.first = true;
        }
    }

    @Override
    public void webcamImageObtained(WebcamEvent we) {
        if(!enabled)
            return;
        if(detectedHotSpots >= laneHotSpots.size())
            return;
        BufferedImage temp = we.getImage();
        if(temp == image){
            return;
        }
        image = temp;
        for (HotSpot hotSpot: laneHotSpots.values()) {
            if (hotSpot.detected)
                continue;
            int currentPixel = image.getRGB(hotSpot.position.x, hotSpot.position.y);
            if (hotSpot.first) {
                hotSpot.pixel = currentPixel;
                hotSpot.first = false;
                continue;
            }
            if (significantDifference(hotSpot.pixel, currentPixel)) {
                detectedHotSpots++;
                hotSpot.detected = true;
                notifyHotSpotListeners(hotSpot);
                System.out.println("hotspot detected at " + hotSpot.position.x);
            }

        }
    }

    private void notifyHotSpotListeners(HotSpot hotSpot) {
        hotSpotListeners.parallelStream().forEach(l ->l.hotSpotDetected(hotSpot,detectedHotSpots));
    }

    private boolean significantDifference(int p1, int p2) {
        //blue difference
        int difference = Math.abs((p1 & 0xff) - (p2 & 0xff));
        //green difference
        difference += Math.abs((p1>>>8 & 0xff) - (p2>>>8 & 0xff));
        //red difference
        difference += Math.abs((p1>>>16 & 0xff) - (p2>>> 16 & 0xff));
        return difference > sensitivity;

    }

    public void addHotSpotListener(HotSpotListener hotSpotListener) {
        hotSpotListeners.add(hotSpotListener);
    }
}
