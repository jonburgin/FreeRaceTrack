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
public class Runoff extends AbstractRaceParent {
    int take = 1;//number of winners to move up from child raceTypes.

    public Runoff(){
        super();
    }
    public Runoff(String name, int take){
        this();
        this.name = name;
        this.take = take;
    }

    @JsonIgnore
    public Set<String> getCompetitionClasses(){
        return raceTypes.stream()
                .map(race -> race.getCompetitionClasses())
                .flatMap(l->l.stream())
                .collect(Collectors.toSet());
    }

    public String toString(){
        return getCompetitionClasses().stream()
                .collect(Collectors.joining(",",name + " (",")"));
    }
}
