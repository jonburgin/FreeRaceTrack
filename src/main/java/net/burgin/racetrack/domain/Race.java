package net.burgin.racetrack.domain;

import lombok.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
public class Race extends AbstractRace{

    Set<String> competitionClasses = new HashSet<>();

    public Race(){}

    public Race(String name, Collection<String> competitionClasses){
        this();
        this.name = name;
        this.competitionClasses.addAll(competitionClasses);
    }

    public Race(String name, String... competitionClasses){
        this(name, Arrays.asList(competitionClasses));
    }

    public String toString(){
        return name;
    }
}
