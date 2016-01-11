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
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 11/15/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OneTimeHotSpotDetector extends WebcamAdapter implements HotSpotDetector {

    int sensitivity = 30;
    List<HotSpot> laneHotSpots = new ArrayList<>();
    int detectedHotSpots = 0;
    BufferedImage image;
    List <DetectionEventListener> detectionEventListeners = new ArrayList<>();
    boolean enabled = true;

    public void addHotSpotPoint(Point point){
        HotSpot hotSpot = new HotSpot(point);
        if(!laneHotSpots.contains(hotSpot))
            laneHotSpots.add( hotSpot);
    }

    public void removeHotSpots(){
        laneHotSpots.clear();
    }

    public void reset(){
        System.out.println("resetting");
        detectedHotSpots = 0;
        laneHotSpots.stream()
                .forEach((hs) ->hs.reset());
        notifyDetectionEventListeners(new DetectionEvent());
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
        boolean foundOne = false;
        for (HotSpot hotSpot: laneHotSpots) {
            if (hotSpot.detected)
                continue;
            int currentPixel = image.getRGB(hotSpot.position.x, hotSpot.position.y);
            if (hotSpot.needsInitialization) {
                hotSpot.pixel = currentPixel;
                hotSpot.needsInitialization = false;
                continue;
            }
            if (significantDifference(hotSpot.pixel, currentPixel)) {
                detectedHotSpots++;
                hotSpot.detected = true;
                foundOne = true;
                System.out.println("hotspot detected at " + (laneHotSpots.indexOf(hotSpot) + 1));
            }
        }
        if(foundOne){
            List<HotSpot> detectedHotSpots = laneHotSpots.stream()
                    .filter(hotSpot -> hotSpot.isDetected())
                    .collect(Collectors.toList());
            notifyDetectionEventListeners(new DetectionEvent(detectedHotSpots,image));
        }

    }

    private void notifyDetectionEventListeners(DetectionEvent detectionEvent) {
        detectionEventListeners.parallelStream().forEach(l ->l.eventDetected(detectionEvent));
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

    public void addHotSpotListener(DetectionEventListener detectionEventListener) {
        detectionEventListeners.add(detectionEventListener);
    }
}
