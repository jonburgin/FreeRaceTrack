package net.burgin.racetrack.detection;

import com.github.sarxos.webcam.WebcamListener;
import net.burgin.racetrack.domain.Heat;

import java.util.List;


/**
 * Created by jonburgin on 12/3/15.
 */
public interface HotSpotDetector extends WebcamListener{
    void clearHotSpots();
    void addHotSpot(HotSpot hotSpot);
    void addHotSpots(List<HotSpot> hotSpots);
    void addHotSpotListener(DetectionEventListener detectionEventListener);
    void removeHotSpotListener(DetectionEventListener detectionEventListener);
    void setEnabled(boolean enabled);
    void reset();
}
