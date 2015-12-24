package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.racing.DefaultHeatGenerator;
import net.burgin.racetrack.racing.HeatGenerator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class RaceEvent extends AbstractRaceParent {
    Set<String> competitionClasses = new HashSet<>();
    List<Racer> racers = new ArrayList<>();
    Map<RaceType, Heat> heats = new HashMap<>();
    Map<RaceType, RaceResult> results = new HashMap<>();
    Track track;
    @JsonIgnore
    HeatGenerator heatGenerator = new DefaultHeatGenerator();

    public void assignCarIds(){
        Optional<Car> carWithId = findCarById(findMaxCarId());
        Car maxCar;
        if(carWithId.isPresent()) {
            maxCar = carWithId.get();
        }else {
            maxCar = new Car();
            maxCar.setId(0);
        }
        getCars().stream()
                .filter(car -> car.getId()==0)
                .reduce(maxCar,(lastCarAssigned,carToBeAssigned)->{carToBeAssigned.setId(lastCarAssigned.getId()+1);return carToBeAssigned;});

    }

    private Optional<Car> findCarById(int id){
        return getCars().stream()
                .filter(car->car.getId() == id)
                .findFirst();
    }

    private int findMaxCarId(){
        OptionalInt foundId = getCars().stream()
                .mapToInt(Car::getId)
                .max();
        return(Math.max(foundId.getAsInt(),1));
    }

    public List<Car> getCars(){
        return racers.stream()
                .map(Racer::getCars)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
