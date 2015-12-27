package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RunoffRace extends AbstractRaceParent {
    int takeNumber = 1;//number of winners to move up from child raceTypes.

    public RunoffRace(){
        super();
    }
    public RunoffRace(String name, int takeNumber){
        this();
        this.name = name;
        this.takeNumber = takeNumber;
    }

    @JsonIgnore
    public Set<String> getCompetitionClasses(){
        return races.stream()
                .map(Race::getCompetitionClasses)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public String toString(){
        return getCompetitionClasses().stream()
                .collect(Collectors.joining(",",name + " (",")"));
    }

    public boolean childrenNotReady(){
        return getRaces().stream()
                .anyMatch(Race::hasHeatsToRun);
    }
}
