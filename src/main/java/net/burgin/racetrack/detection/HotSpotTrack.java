package net.burgin.racetrack.detection;

import lombok.Data;
import net.burgin.racetrack.domain.Track;

import java.util.*;
import java.awt.*;
import java.util.List;

/**
 * Created by jonburgin on 12/2/15.
 */
@Data
public class HotSpotTrack implements Track{
    private Point finishLinePosition = new Point(20, 200);
    private int width = 200;
    private Map<Integer, HotSpot> lanes = new HashMap();
    private HotSpot raceStartHotSpot = new HotSpot(-1,new Point(300, 200));

    public HotSpotTrack() {
        setLaneCount(2);
    }

    public HotSpotTrack(int quantity, Point finishLinePosition, int width) {
        setLaneCount(quantity);
        this.finishLinePosition = finishLinePosition;
        this.width = width;
    }

    public void setLaneCount(int quantity){
        lanes.clear();
        int division = width / (quantity + 1);
        for(int i=0; i < quantity; i++){
            lanes.put(i,new HotSpot(i,new Point(finishLinePosition.x + (i+1)* division, finishLinePosition.y)));
        }
    }

    public void setFinishLinePosition(Point position){
        this.finishLinePosition = position;
        setLaneCount(lanes.size());
    }

    public int getLaneCount(){
        return lanes.size();
    }

    public Point getMoveHotSpot(){
        return new Point(finishLinePosition.x - 5, finishLinePosition.y);
    }

    public Point getStretchHotSpot(){
        return new Point(finishLinePosition.x + width + 5, finishLinePosition.y);
    }

    public void adjustWidth(Point point) {
        if(point.x <= finishLinePosition.x)
            return;
        width = point.x - finishLinePosition.x;
        setLaneCount(lanes.size());
    }
}
