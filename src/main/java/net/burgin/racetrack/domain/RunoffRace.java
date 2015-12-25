package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RunoffRace extends AbstractRaceParent {
    int takeNumber = 1;//number of winners to move up from child raceTypes.
    boolean byClassification;//takeNumber of winners per classification, instead of the entire field of competitors

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
                .map(race -> race.getCompetitionClasses())
                .flatMap(l->l.stream())
                .collect(Collectors.toSet());
    }

    public String toString(){
        return getCompetitionClasses().stream()
                .collect(Collectors.joining(",",name + " (",")"));
    }
}
