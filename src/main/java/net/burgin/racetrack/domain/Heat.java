package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.awt.*;
import java.util.List;

/**
 * Created by jonburgin on 12/2/15.
 *
 * This class represent a particular run within a race
 * competitors position within the list corresponds to their lane number
 */
@Data
public class Heat {
    List<Competitor> competitors;
    long startTime;
    @JsonIgnore
    Image photofinish;

    public Heat(){}
    public Heat(List<Competitor> competitors){
        this();
        this.competitors = competitors;
    }
}
