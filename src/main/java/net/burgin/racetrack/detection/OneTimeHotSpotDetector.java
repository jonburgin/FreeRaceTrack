package net.burgin.racetrack.detection;

import com.github.sarxos.webcam.WebcamEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.gui.adapters.WebcamAdapter;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 11/15/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OneTimeHotSpotDetector extends WebcamAdapter implements HotSpotDetector {

    int sensitivity = 30;
    List<HotSpot> hotSpots = new ArrayList<>();
    int detectedHotSpots = 0;
    BufferedImage image;
    List <DetectionEventListener> detectionEventListeners = new ArrayList<>();
    boolean enabled = true;//todo is this necessary?

    public void addHotSpot(HotSpot hotSpot){
        if(!hotSpots.contains(hotSpot))
            hotSpots.add( hotSpot);
    }

    public void reset(){
        System.out.println("resetting");
        detectedHotSpots = 0;
        hotSpots.stream()
                .forEach((hs) ->hs.reset());
        notifyDetectionEventListeners(new DetectionEvent());
    }


    @Override
    public void webcamImageObtained(WebcamEvent we) {
        if(!enabled)
            return;
        if(detectedHotSpots >= hotSpots.size())
            return;
//        BufferedImage temp = we.getImageIcon();
//        if(temp == image){
//            return;
//        }
//        image = temp;
        image = we.getImage();
        boolean foundOne = false;
        long currentTime = System.currentTimeMillis();
        for (HotSpot hotSpot: hotSpots) {
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
                System.out.println("hotspot detected at " + (hotSpots.indexOf(hotSpot) + 1));
            }
        }
        if(foundOne){
            List<HotSpot> detectedHotSpots = hotSpots.stream()
                    .filter(hotSpot -> hotSpot.isDetected())
                    .collect(Collectors.toList());
            notifyDetectionEventListeners(new DetectionEvent(currentTime, detectedHotSpots,image));
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
