package net.burgin.racetrack.racing;

import net.burgin.racetrack.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by jonburgin on 12/11/15.
 */
public class DefaultHeatGenerator implements HeatGenerator {

    @Override
    public List<Heat> generateRaceHeats(RaceEvent raceEvent){
        List<Race> races = getLeafRaces(raceEvent);
        List<Car> cars = raceEvent.getCars();
        Track track = raceEvent.getTrack();
        return races.stream()
                .map(race -> generateHeats(race, cars, track))
                .flatMap(l->l.stream())
                .collect(Collectors.toList());
    }

    //TODO generateRunoffHeats(raceEvent, Results list)

    List<Race> getLeafRaces(RaceParent raceParent){
        return  raceParent.getRaceTypes().stream()
                .map(raceType -> raceType instanceof Race? Arrays.asList((Race)raceType): getLeafRaces((RaceParent)raceType))
                .flatMap(raceList->raceList.stream())
                .collect(Collectors.toList());
    }


    @Override
    public List<Heat> generateHeat(RaceType race, List<Car> cars, Track track) {
        if(race instanceof Race){
            return generateHeats((Race)race,cars, track);
        }else{
            return null;//todo generateHeat from winners...need to get a way to connect raceTypes to winners
        }
    }

    List<Heat> generateHeats(Race race, List<Car> cars, Track track){
        int numberOfLanes = track.getLaneCount();
        Set<String> competionClasses = race.getCompetitionClasses();
        List<Car> validCars = cars.stream()
                .filter(car -> competionClasses.contains(car.getCompetitionClass()))
                .collect(Collectors.toList());
        List<Heat> heats = IntStream.range(0, validCars.size())
                .mapToObj(i -> {
                    //a list of competitors for available lanes
                    List<Competitor> competitors = createCompetitors(validCars,numberOfLanes);
                    //shift the cars
                    validCars.add(validCars.remove(0));
                    return competitors;
                })
                .map(list -> new Heat(list))
                .collect(Collectors.toList());
        return heats;
    }

    private List<Competitor> createCompetitors(List<Car> cars, int limit){
        return cars.stream()
                .limit(limit)
                .map(car -> new Competitor(car))
                .collect(Collectors.toList());
    }
}
