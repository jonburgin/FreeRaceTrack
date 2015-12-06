package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.awt.*;
import java.util.List;

/**
 * Created by jonburgin on 12/2/15.
 */
@Data
public class Heat {
    List<Competitor> competitors;
    long startTime;
    @JsonIgnore
    Image photofinish;
}
