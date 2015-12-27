package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/24/15.
 */
@Data
public class DefaultRaceResult implements RaceResult{
    @JsonIgnore
    private final Race race;
    List<List<Competitor>> results;

    public DefaultRaceResult(Race race){
        this.race = race;
        if(race.hasHeatsToRun())
            throw new IllegalStateException("Not all heats have run");
        List<Competitor> list =
                race.getHeats().stream()
                .peek(this::normalizeRaceTime)
                .map(Heat::getCompetitors)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Competitor::getCar))
                .values().stream()
                .map(this::computeAverageTime)
                .collect(Collectors.toList());
        if(race instanceof SimpleRace && ((SimpleRace)race).isByClassification())
            results = new ArrayList(resultsByCompetitionClass(list));
        else
            results =  Arrays.asList(list);
    }

    Collection<List<Competitor>> resultsByCompetitionClass(List<Competitor> list){
        Map<String, List<Competitor>> collect = list.stream()
                .collect(Collectors.groupingBy(competitor -> competitor.getCar().getCompetitionClass()));
        return collect.values();
    }

    Competitor computeAverageTime(List<Competitor> competitors){
        Competitor competitor = new Competitor(competitors.get(0).getCar());
        competitor.setAverageTime( competitors.stream()
                .mapToLong(Competitor::getRaceTime)
                .average().getAsDouble());
        return competitor;
    }

    Heat normalizeRaceTime(Heat heat){
        long startTime = heat.getStartTime();
        heat.getCompetitors().stream().forEach(competitor -> competitor.setRaceTime(competitor.getRaceTime()-startTime));
        heat.setStartTime(startTime);
        return heat;
    }

}
