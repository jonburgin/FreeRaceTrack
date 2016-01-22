package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.detection.DetectionEvent;
import net.burgin.racetrack.detection.DetectionEventListener;
import net.burgin.racetrack.detection.HotSpot;
import net.burgin.racetrack.detection.HotSpotDetector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/2/15.
 *
 * This class represent a particular run within a race
 * competitors position within the list corresponds to their lane number
 */
@Data
@EqualsAndHashCode(exclude = "uuid")
public class Heat implements DetectionEventListener{
    List<Competitor> competitors;
    UUID uuid = UUID.randomUUID();//for saving the photofinish
    String name;
    long startTime;
    @JsonIgnore
    List<Image> photofinish = new Vector<>();

    public Heat(){}
    public Heat(Race race, List<Vehicle> vehicles){
        name = race.getName();
        competitors = vehicles.stream()
            .map(car -> new Competitor(car))
            .collect(Collectors.toList());
    }

    public String toString(){
        return competitors.stream()
                .map(competitor -> competitor.getVehicle().toString())
                .collect(Collectors.joining("', '", name + " ['", "']"));
    }

    @Override
    public void eventDetected(DetectionEvent detectionEvent) {
        if(detectionEvent.getHotSpots().size() < 1){
            reset();
            return;
        }
        long time = detectionEvent.getTime();
        System.out.println("Adding photo " );
        photofinish.add(detectionEvent.getImage());
        detectionEvent.getHotSpots().stream()
                .forEach((hotSpot -> updateTime(hotSpot, time)));
    }

    private void updateTime(HotSpot hotSpot, long time) {
        if(hotSpot.getLane() == 0 && startTime != 0) {
            startTime = time;
            System.out.println("start time " + startTime);
        }
        else if(hotSpot.getLane() > 0){
            Competitor competitor = competitors.get(hotSpot.getLane());
            if(competitor.raceTime == 0) {
                competitor.setRaceTime(time);
                System.out.println("lane " + hotSpot.getLane() + " time " + time);
            }
        }
    }

    public void reset(){
        startTime = 0;
        photofinish.clear();
        competitors.stream()
                .forEach(competitor -> competitor.setRaceTime(0));
    }
}
