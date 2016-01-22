package net.burgin.racetrack.detection;

import lombok.Data;
import net.burgin.racetrack.domain.Track;

import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by jonburgin on 12/2/15.
 */
@Data
public class HotSpotTrack implements Track{
    private Point finishLinePosition = new Point(20, 200);
    private int width = 200;
    private List<HotSpot> lanes = new ArrayList<>();
    private HotSpot raceStartHotSpot = new HotSpot(0,new Point(300, 200));

    private static HotSpotTrack instance = new HotSpotTrack(3);
    public static HotSpotTrack getInstance(){
        return instance;
    }

    private HotSpotTrack(int laneCount){
        setLaneCount(laneCount);
    }

    public void setLaneCount(int quantity){
        lanes.clear();
        int division = width / (quantity - 1);
        for(int i=0; i < quantity; i++){
            lanes.add(new HotSpot(i+1, new Point(finishLinePosition.x + (i)* division, finishLinePosition.y)));
        }
    }

    public void adjustHotSpots(){
        int division = width / (lanes.size() - 1);
        lanes.stream()
                .forEach(hs -> hs.setPosition(new Point(finishLinePosition.x + (hs.getLane()-1)* division, finishLinePosition.y)));
    }

    public void setFinishLinePosition(Point position){
        this.finishLinePosition = position;
        adjustHotSpots();
    }

    public void setRaceStartPosition(Point point){
        raceStartHotSpot.setPosition(point);
    }

    public Point getRaceStartPosition(){
        return raceStartHotSpot.getPosition();
    }

    public int getLaneCount(){
        return lanes.size();
    }

    public Point getMovePosition(){
        return new Point(finishLinePosition.x - 5, finishLinePosition.y);
    }

    public Point getStretchPosition(){
        return new Point(finishLinePosition.x + width + 5, finishLinePosition.y);
    }

    public void adjustWidth(Point point) {
        if(point.x <= finishLinePosition.x)
            return;
        width = point.x - finishLinePosition.x;
        adjustHotSpots();
    }
}
