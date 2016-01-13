package net.burgin.racetrack.detection;

import com.github.sarxos.webcam.WebcamListener;

import java.util.List;


/**
 * Created by jonburgin on 12/3/15.
 */
public interface HotSpotDetector extends WebcamListener{
    void addHotSpot(HotSpot hotSpot);
    void setHotSpots(List<HotSpot> hotSpots);
    void addHotSpotListener(DetectionEventListener detectionEventListener);
    void setEnabled(boolean enabled);
}
