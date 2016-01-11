package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by jonburgin on 12/11/15.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "@CLASS")
public interface Track {
    double DEFAULT_SCALE=1/25;
    @JsonIgnore
    default double getScale(){return DEFAULT_SCALE;};
    void setLaneCount(int count);
    int getLaneCount();
}
