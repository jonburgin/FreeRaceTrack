package net.burgin.racetrack.domain;

import lombok.Data;

/**
 * Created by jonburgin on 12/24/15.
 */
@Data
public class DefaultTrack implements Track {
    int laneCount = 0;
    public DefaultTrack(int laneCount){
        this.laneCount = laneCount;
    }
}
