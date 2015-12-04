package net.burgin.racetrack.detection;

import net.burgin.racetrack.detection.HotSpot;

/**
 * Created by jonburgin on 12/2/15.
 */
public interface HotSpotListener {
    void hotSpotDetected(HotSpot hotSpot, int count);
}
