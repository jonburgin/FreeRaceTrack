package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by jonburgin on 12/4/15.
 */
@Getter
@Setter
public class Race extends AbstractRaceEventChangeNotifier{
    String name;
    Set<String> classes = new HashSet<>();

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
        raceChanged(this);
    }
}
