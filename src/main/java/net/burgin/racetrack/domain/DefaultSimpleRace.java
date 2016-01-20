package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultSimpleRace extends AbstractRace implements SimpleRace{

    List<String> competitionClasses = new ArrayList<>();
    /**
     * if true, this allows for cars of different competition classes to race together, but not actually compete against each other
     */
    boolean byClassification;

    public DefaultSimpleRace(){}

    public DefaultSimpleRace(String name, Collection<String> competitionClasses){
        this();
        this.name = name;
        this.competitionClasses.addAll(competitionClasses);
    }

    public DefaultSimpleRace(String name, String... competitionClasses){
        this(name, Arrays.asList(competitionClasses));
    }
    public DefaultSimpleRace(String name, Boolean byClassification, String... competitionClasses){
        this(name, Arrays.asList(competitionClasses));
        this.byClassification = byClassification;
    }

    public String toString(){
        return name;
    }

}
