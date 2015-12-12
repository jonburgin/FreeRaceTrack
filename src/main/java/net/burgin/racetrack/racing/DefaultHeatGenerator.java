package net.burgin.racetrack.racing;

import net.burgin.racetrack.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by jonburgin on 12/11/15.
 */
public class DefaultHeatGenerator implements HeatGenerator {
    @Override
    public List<Heat> generate(RaceType race, List<Car> cars, Track track) {
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
