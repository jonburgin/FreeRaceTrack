package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by jonburgin on 12/11/15.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "@CLASS")
public interface Track {
    void setLaneCount(int count);
    int getLaneCount();
}
