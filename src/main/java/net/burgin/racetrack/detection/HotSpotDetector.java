package net.burgin.racetrack.detection;

import com.github.sarxos.webcam.WebcamListener;

import java.awt.*;

/**
 * Created by jonburgin on 12/3/15.
 */
public interface HotSpotDetector extends WebcamListener{
    void addHotSpotPoint(Point point);
    void addHotSpotListener(DetectionEventListener detectionEventListener);
    void removeHotSpots();
    void setEnabled(boolean enabled);
}
