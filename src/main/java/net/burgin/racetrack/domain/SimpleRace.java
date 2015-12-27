package net.burgin.racetrack.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleRace extends AbstractRace{

    Set<String> competitionClasses = new HashSet<>();
    /**
     * if true, this allows for cars of different competition classes to race together, but not actually compete against each other
     */
    boolean byClassification;

    public SimpleRace(){}

    public SimpleRace(String name, Collection<String> competitionClasses){
        this();
        this.name = name;
        this.competitionClasses.addAll(competitionClasses);
    }

    public SimpleRace(String name, String... competitionClasses){
        this(name, Arrays.asList(competitionClasses));
    }

    public String toString(){
        return name;
    }
}
