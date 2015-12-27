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
        List<Car> validCars = race instanceof SimpleRace?getCarsForSimpleRace((SimpleRace)race):getCarsForRunoffRace((RunoffRace)race);
        race.setHeats(generateHeats(race, validCars, track.getLaneCount()));
    }

    protected List<Car> getCarsForRunoffRace(RunoffRace runoff){
        if(runoff.childrenNotReady())
            return new ArrayList<>();
        return runoff.getRaces().stream()
                .map(race -> new DefaultRaceResult(race).getResults())
                .flatMap(List::stream)
                .map(l -> l.subList(0, runoff.getTakeNumber()))
                .flatMap(List::stream)
                .map(Competitor::getCar)
                .collect(Collectors.toList());
    }


    protected List<Car> getCarsForSimpleRace(SimpleRace simpleRace){
        Set<String> competionClasses = simpleRace.getCompetitionClasses();
        return raceEvent.getCars().stream()
                .filter(car -> competionClasses.contains(car.getCompetitionClass()))
                .collect(Collectors.toList());
    }

    protected List<Heat> generateHeats(Race race, List<Car> cars, int numberOfLanes){
        List<Heat> heats = IntStream.range(0, cars.size())
                .mapToObj(i -> {
                    List<Car> competitors = cars.stream()
                            .limit(numberOfLanes)
                            .collect(Collectors.toList());
                    cars.add(cars.remove(0));//rotate the cars
                    return competitors;
                })
                .map(list -> new Heat(race,list))
                .collect(Collectors.toList());
        return heats;
    }

}
