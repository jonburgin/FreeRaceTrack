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
public class GeometricTrack  implements Track{
    Point finishLinePosition = new Point(20, 200);
    int width = 200;
    Map<Integer, Point> lanes = new HashMap();
    private Point raceStartHotSpot = new Point(300, 200);
    public GeometricTrack() {
        setLaneCount(2);
    }

    public GeometricTrack(int quantity, Point finishLinePosition, int width) {
        setLaneCount(quantity);
        this.finishLinePosition = finishLinePosition;
        this.width = width;
    }

    public void setLaneCount(int quantity){
        lanes.clear();
        int division = width / (quantity + 1);
        for(int i=0; i < quantity; i++){
            lanes.put(i,new Point(finishLinePosition.x + (i+1)* division, finishLinePosition.y));
        }
    }

    public void setFinishLinePosition(Point position){
        this.finishLinePosition = position;
        setLaneCount(lanes.size());
    }

    public int getLaneCount(){
        return lanes.size();
    }

    public Optional<Integer> getLaneNumber(Point point) {
       return lanes.keySet().stream().filter(k->lanes.get(k).equals(point)).findFirst();
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
