package net.burgin.racetrack.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by jonburgin on 12/4/15.
 */
@Getter
@Setter
public class Race {
    String name;
    Set<String> classes = new HashSet<>();
    List<Heat> heats = new ArrayList<>();
    List<Car> cars = new ArrayList<>();
    RaceEvent raceEvent;
    RaceParent raceParent;

    public Race(){}
    public Race(String name, String... classes){
        this.name = name;
        this.classes.addAll( Arrays.asList(classes));
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String name){
        this.name = name;
        raceEvent.raceChanged(this);
    }
}
