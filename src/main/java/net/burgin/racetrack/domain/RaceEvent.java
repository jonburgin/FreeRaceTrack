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
@EqualsAndHashCode(callSuper=true,exclude = "heatGenerator")
public class RaceEvent extends AbstractRaceParent {
    List<String> competitionClasses = new ArrayList<>();
    List<Participant> participants = new ArrayList<>();
    Track track = new DefaultTrack(6);//todo remove this
    @JsonIgnore
    HeatGenerator heatGenerator = new DefaultHeatGenerator(this);

    public void assignVehicleIds(){
        Optional<Vehicle> vehicleWithId = findVehicleById(findMaxVehicleId());
        Vehicle maxVehicle = vehicleWithId.isPresent()?vehicleWithId.get():new Vehicle();
        getVehicles().stream()
                .filter(car -> car.getId()==0)
                .reduce(maxVehicle,(lastCarAssigned, carToBeAssigned)->{carToBeAssigned.setId(lastCarAssigned.getId()+1);return carToBeAssigned;});

    }

    private Optional<Vehicle> findVehicleById(int id){
        return getVehicles().stream()
                .filter(car->car.getId() == id)
                .findFirst();
    }

    private int findMaxVehicleId(){
        OptionalInt foundId = getVehicles().stream()
                .mapToInt(Vehicle::getId)
                .max();
        return foundId.getAsInt();
    }

    @JsonIgnore
    public List<Vehicle> getVehicles(){
        return participants.stream()
                .map(Participant::getVehicles)
                .flatMap(List::stream)
                .filter(car -> competitionClasses.contains(car.getCompetitionClass()))
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Vehicle> getVehicles(SimpleRace simpleRace){
        List<String> competionClasses = simpleRace.getCompetitionClasses();
        return getVehicles().stream()
                .filter(car -> competionClasses.contains(car.getCompetitionClass()))
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Race> getRacesAsList(){
        List<Race> list = new ArrayList<>();
        getRaces().stream()
                .forEach(r->list.addAll(getRacesAsList(r)));
        return list;
    }

    @JsonIgnore
    protected List<Race> getRacesAsList(Race race){
        if(race instanceof DefaultSimpleRace)
            return Arrays.asList(race);
        List<Race> list =
                ((RaceParent)race).getRaces().stream()
                    .map(this::getRacesAsList)
                    .flatMap(l->l.stream())
                    .collect(Collectors.toList());
        list.add(race);
        return list;
    }

    @JsonIgnore
    public List<Heat> getHeats(){
        return getRacesAsList().stream()
                .map(race -> race.getHeats())
                .flatMap(l->l.stream())
                .collect(Collectors.toList());
    }

    public void generateHeats(){
        heatGenerator.generateAllRaceHeats();
    }

    public List<RaceResult> generateResults(){
        List<Race> races  = getRacesAsList();
        return races.stream()
                .map(DefaultRaceResult::determineRaceResults)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public Participant getRacerForVehicle(Vehicle vehicle) {
        Optional<Participant> first = this.getParticipants().stream()
                .filter(racer -> racer.getVehicles().contains(vehicle))
                .findFirst();
        return first.get();
    }
}
