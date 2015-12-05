package net.burgin.racetrack.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonburgin on 12/4/15.
 */
@Getter
@Setter
public class Runoff extends Race implements RaceParent{
    List<Race> childRaces = new ArrayList<>();
    int take;//number of winners to move up from child races.

    public Runoff(){
        super();
    }
    public Runoff(String name, int take){
        this();
        this.name = name;
        this.take = take;
    }

    public void setRaceEvent(RaceEvent raceEvent){
        this.raceEvent = raceEvent;
        childRaces.stream().forEach(c->c.setRaceEvent(raceEvent));
    }

    public void addRace(Race race){
        childRaces.add(race);
        race.setRaceParent(this);
        race.setRaceEvent(raceEvent);
        classes.addAll(race.classes);
        raceEvent.raceAdded(this, race, childRaces.indexOf(race));
    }

    public void removeRace(Race race){
        int index = childRaces.indexOf(race);
        childRaces.remove(index);
        race.setRaceParent(null);
        race.setRaceEvent(null);
        raceEvent.raceRemoved(this, race, index);
        adoptClassesOfChildRaces();
    }

    private void adoptClassesOfChildRaces(){
        classes.clear();
        childRaces.stream().forEach(r->classes.addAll(r.classes));
    }

    @Override
    public int indexOf(Race race) {
        return childRaces.indexOf(race);
    }

    @Override
    public String toString(){
        return name;
    }
}
