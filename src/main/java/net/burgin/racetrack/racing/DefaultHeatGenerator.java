package net.burgin.racetrack.racing;

import net.burgin.racetrack.domain.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by jonburgin on 12/11/15.
 */
public class DefaultHeatGenerator implements HeatGenerator {
    RaceEvent raceEvent;

    public DefaultHeatGenerator(RaceEvent raceEvent){
        this.raceEvent = raceEvent;
    }

    @Override
    public void generateAllRaceHeats(){
        raceEvent.getRacesAsList().stream()
                .filter(race -> !race.hasHeats())
                .forEach(this::generateHeatsForRace);
    }

    public void generateHeatsForRace(Race race){
        Track track = raceEvent.getTrack();
        List<Vehicle> validVehicles = race instanceof SimpleRace ?(raceEvent.getVehicles((SimpleRace)race)):((RunoffRace)race).getVehicles();
        race.setHeats(generateHeats(race, validVehicles, track.getLaneCount()));
    }

    protected List<Heat> generateHeats(Race race, List<Vehicle> vehicles, int numberOfLanes){
        List<Heat> heats = IntStream.range(0, vehicles.size())
                .mapToObj(i -> {
                    List<Vehicle> competitors = vehicles.stream()
                            .limit(numberOfLanes)
                            .collect(Collectors.toList());
                    vehicles.add(vehicles.remove(0));//rotate the cars
                    return competitors;
                })
                .map(list -> new Heat(race,list))
                .collect(Collectors.toList());
        return heats;
    }

}
