package net.burgin.racetrack.domain;

import lombok.Data;

import java.util.Set;

/**
 * Created by jonburgin on 12/11/15.
 */
@Data
abstract public class AbstractRace extends AbstractRaceEventChangeNotifier implements RaceType{
    String name;

    public void setName(String name){
        this.name = name;
        raceChanged((RaceType)this);
    }

    @Override
    public String toString(){
        return name;
    }

}