package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    UUID raceId;
    @JsonIgnore
    Image photofinish;

    public Heat( UUID raceId,List<Competitor> competitors){
        this.raceId = raceId;
        this.competitors = competitors;
    }

    public String toString(){
        return competitors.stream()
                .map(competitor -> competitor.getCar().getName())
                .collect(Collectors.joining("', '","['","']"));
    }
}
