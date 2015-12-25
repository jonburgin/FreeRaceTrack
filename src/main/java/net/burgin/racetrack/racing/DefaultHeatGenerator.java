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
    public List<Heat> generateAllSimpleRaceHeats(){
        return getLeafRaces(raceEvent).stream()
                .map(race -> generateHeatsForSimpleRace(race))
                .flatMap(l->l.stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Heat> generateHeatsForRunoffRace(RunoffRace runoff){
        List<DefaultRaceResult> results = runoff.getRaces().stream()
                .map(racetype ->raceEvent.getResults().get(racetype.getId()))
                .collect(Collectors.toList());
        List<Car> cars = results.stream()
                .map(result -> getCompetitorsFromSubRace(runoff, result))
                .flatMap(competitorsList -> competitorsList.stream())
                .map(competitor -> competitor.getCar())
                .collect(Collectors.toList());
        return generateHeats(runoff.getId(), cars,raceEvent.getTrack().getLaneCount());
    }

    List<Competitor> getCompetitorsFromSubRace(RunoffRace runoff, RaceResult raceResult){
        long take = runoff.getTakeNumber();
        Set<String> raceClassifications = runoff.isByClassification()?
                raceResult.getRaceClassifications():new HashSet(Arrays.asList(RaceResult.ALL_COMPETITION_CLASSES));
        return raceClassifications.stream()
                .map(c -> raceResult.getResults().get(c))
                .limit(take)
                .flatMap(lists->lists.stream())
                .collect(Collectors.toList());
    }

    List<SimpleRace> getLeafRaces(RaceParent raceParent){
        return  raceParent.getRaces().stream()
                .map(raceType -> raceType instanceof SimpleRace ? Arrays.asList((SimpleRace)raceType): getLeafRaces((RaceParent)raceType))
                .flatMap(raceList->raceList.stream())
                .collect(Collectors.toList());
    }


    @Override
    public List<Heat> generateHeatsForSimpleRace(SimpleRace simpleRace){
        List<Car> cars = raceEvent.getCars();
        Track track = raceEvent.getTrack();
        Set<String> competionClasses = simpleRace.getCompetitionClasses();
        List<Car> validCars = cars.stream()
                .filter(car -> competionClasses.contains(car.getCompetitionClass()))
                .collect(Collectors.toList());
        return generateHeats(simpleRace.getId(), validCars,track.getLaneCount());
    }

    protected List<Heat> generateHeats(UUID id, List<Car> cars, int numberOfLanes){
        List<Heat> heats = IntStream.range(0, cars.size())
                .mapToObj(i -> {
                    List<Competitor> competitors = createNumberCompetitorsFromCarList(cars,numberOfLanes);
                    cars.add(cars.remove(0));//rotate the cars
                    return competitors;
                })
                .map(list -> new Heat(id,list))
                .collect(Collectors.toList());
        return heats;

    }

    protected List<Competitor> createNumberCompetitorsFromCarList(List<Car> cars, int limit){
        return cars.stream()
                .limit(limit)
                .map(car -> new Competitor(car))
                .collect(Collectors.toList());
    }
}
