package net.burgin.racetrack.detection;

import com.github.sarxos.webcam.WebcamEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.gui.adapters.WebcamAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 11/15/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OneTimeHotSpotDetector extends WebcamAdapter implements HotSpotDetector {

    int sensitivity = 80;
    List<HotSpot> hotSpots = new ArrayList<>();
    int detectedHotSpots = 0;
    BufferedImage image;
    Set<DetectionEventListener> detectionEventListeners = new HashSet<>();
    boolean enabled = true;//todo is this necessary?

    @Override
    public void clearHotSpots() {
        hotSpots.clear();
    }

    public void addHotSpot(HotSpot hotSpot){
        if(!hotSpots.contains(hotSpot))
            hotSpots.add( hotSpot);
    }

    @Override
    public void addHotSpots(List<HotSpot> hotSpots) {
        hotSpots.stream()
                .forEach(hotSpot -> addHotSpot(hotSpot));
    }

    public void reset(){
        System.out.println("resetting hotspot detector!");
        detectedHotSpots = 0;
        hotSpots.stream()
                .forEach(h-> h.reset());

        //todo this might not be the best pattern here, to send a detection event to say that nothing is detected.
        notifyDetectionEventListeners(new DetectionEvent());
    }

    @Override
    public void webcamImageObtained(WebcamEvent we) {
        if(!enabled ||(detectedHotSpots >= hotSpots.size()))
            return;
        image = we.getImage();
        HotSpot triggerHotSpot = hotSpots.get(0);
        Point triggerPosition = triggerHotSpot.getPosition();
        if(!triggerHotSpot.isDetected() && triggerHotSpot.isUninitialized()){
            triggerHotSpot.setPixel(image.getRGB(triggerPosition.x, triggerPosition.y));
                triggerHotSpot.uninitialized = false;
            return;
        }
        boolean raceHasStarted =triggerHotSpot.isDetected() ||
                significantDifference(triggerHotSpot.getPixel(),image.getRGB(triggerPosition.x,triggerPosition.y));
        if(!raceHasStarted)
            return;
        boolean foundOne = false;
        long currentTime = System.currentTimeMillis();
        for (HotSpot hotSpot: hotSpots) {
            if (hotSpot.detected)
                continue;
            Point hotspotPosition = hotSpot.position;
            int currentPixel = image.getRGB(hotspotPosition.x, hotspotPosition.y);
            if (hotSpot.uninitialized) {
                hotSpot.pixel = currentPixel;
                hotSpot.uninitialized = false;
                continue;
            }
            if (significantDifference(hotSpot.pixel, currentPixel)) {
                detectedHotSpots++;
                System.out.println("Detected Hotspots " + detectedHotSpots);
                hotSpot.detected = true;
                foundOne = true;
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
        detectionEventListeners.stream()
                .forEach(l ->l.eventDetected(detectionEvent));
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

    @Override
    public void removeHotSpotListener(DetectionEventListener listener) {
        detectionEventListeners.remove(listener);
    }

}
